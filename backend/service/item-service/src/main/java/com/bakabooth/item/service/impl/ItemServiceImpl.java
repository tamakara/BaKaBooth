package com.bakabooth.item.service.impl;

import com.bakabooth.common.client.FileClient;
import com.bakabooth.item.domain.entity.*;
import com.bakabooth.item.domain.vo.ItemEditFormVO;
import com.bakabooth.item.domain.vo.ItemVO;
import com.bakabooth.item.mapper.*;
import com.bakabooth.item.service.ItemService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {
    private final ItemMapper itemMapper;
    private final ImageMapper imageMapper;
    private final TagMapper tagMapper;
    private final FileClient fileClient;

    @Override
    @Transactional
    public Long createItem(Long userId, ItemEditFormVO formVO) {
        Item item = new Item();
        BeanUtils.copyProperties(formVO, this);
        item.setUserId(userId);
        itemMapper.insert(item);
        return item.getId();
    }

    @Override
    @Transactional
    public Boolean updateItem(Long userId, Long itemId, ItemEditFormVO itemEditFormVO) {
        Item item = itemMapper.selectById(itemId);
        if (!userId.equals(item.getUserId())) {
            throw new RuntimeException("没有权限");
        }
        BeanUtils.copyProperties(itemEditFormVO, item);
        updateById(item);

        imageMapper.delete(new LambdaQueryWrapper<Image>().eq(Image::getItemId, itemId));
        List<Long> images = itemEditFormVO.getImages();
        for (int index = 0; index < images.size(); index++) {
            Image image = new Image();
            image.setItemId(itemId);
            image.setFileId(images.get(index));
            image.setOrderIndex(index);
            imageMapper.insert(image);
        }

        tagMapper.delete(new LambdaQueryWrapper<Tag>().eq(Tag::getItemId, itemId));
        List<String> tags = itemEditFormVO.getTags();
        for (int index = 0; index < tags.size(); index++) {
            Tag tag = new Tag();
            tag.setItemId(itemId);
            tag.setName(tags.get(index));
            tag.setOrderIndex(index);
            tagMapper.insert(tag);
        }

        return true;
    }

    @Override
    @Transactional
    public Boolean deleteItem(Long userId, Long itemId) {
        Item item = itemMapper.selectById(itemId);
        if (!userId.equals(item.getUserId())) {
            throw new RuntimeException("没有权限");
        }

        int n = itemMapper.deleteById(itemId);
        if (n != 1) {
            throw new RuntimeException("删除商品失败");
        }

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public ItemVO getItemVO(Long userId, Long itemId) {
        Item item = itemMapper.selectById(itemId);

        List<String> images = imageMapper
                .selectImagesByItemId(itemId)
                .stream()
                .map(image -> fileClient.getFileUrl(image.getFileId()).getBody())
                .toList();

        List<String> tags = tagMapper
                .selectTagsByItemId(itemId)
                .stream()
                .map(Tag::getName)
                .toList();

        ItemVO vo = new ItemVO();
        BeanUtils.copyProperties(item, vo);
        vo.setImages(images);
        vo.setTags(tags);
        vo.setIsSeller(userId.equals(item.getUserId()));
        if (!vo.getIsSeller()) {
            vo.setStateCode(null);
        }

        return vo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemVO> getItemVOList(Long userId, Long sellerId, Integer stateCode, Integer pageNo, Integer pageSize) {

        Page<Item> page = new Page<>(pageNo, pageSize);

        itemMapper.selectPage(page, new LambdaQueryWrapper<Item>()
                .eq(Item::getUserId, userId)
                .eq(stateCode != 0, Item::getStateCode, stateCode)
        );

        return page
                .getRecords()
                .stream()
                .map(item -> getItemVO(userId, item.getId()))
                .toList();
    }

    @Override
    @Transactional
    public Boolean takeDownItem(Long userId, Long itemId) {
        Item item = itemMapper.selectById(itemId);
        if (!userId.equals(item.getUserId())) {
            throw new RuntimeException("没有权限");
        }
        if (item.getStateCode() == 2) {
            return true;
        }
        if (item.getStateCode() != 1) {
            throw new RuntimeException("商品状态异常");
        }

        lambdaUpdate()
                .eq(Item::getUserId, userId)
                .set(Item::getStateCode, 1);

        return true;
    }

    @Override
    public Boolean putUpItem(Long userId, Long itemId) {
        Item item = itemMapper.selectById(itemId);
        if (!userId.equals(item.getUserId())) {
            throw new RuntimeException("没有权限");
        }
        if (item.getStateCode() == 1) {
            return true;
        }
        if (item.getStateCode() != 2) {
            throw new RuntimeException("商品状态异常");
        }

        lambdaUpdate()
                .eq(Item::getUserId, userId)
                .set(Item::getStateCode, 2);

        return null;
    }

}
