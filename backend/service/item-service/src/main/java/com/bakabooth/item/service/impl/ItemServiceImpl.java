package com.bakabooth.item.service.impl;

import com.bakabooth.common.client.FileClient;
import com.bakabooth.item.domain.entity.*;
import com.bakabooth.item.domain.vo.ItemEditFormVO;
import com.bakabooth.item.domain.vo.ItemPageVO;
import com.bakabooth.item.domain.vo.ItemQueryFormVO;
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
    private final FileClient fileClient;

    @Override
    @Transactional
    public Long createItem(Long userId, ItemEditFormVO formVO) {
        Item item = new Item();
        BeanUtils.copyProperties(formVO, item);
        item.setFavorites(0L);
        item.setUserId(userId);
        itemMapper.insert(item);
        return item.getId();
    }

    @Override
    @Transactional
    public Boolean updateItem(Long userId, Long itemId, ItemEditFormVO formVO) {
        Item item = itemMapper.selectById(itemId);
        if (!userId.equals(item.getUserId())) {
            throw new RuntimeException("没有权限");
        }
        BeanUtils.copyProperties(formVO, item);
        updateById(item);

        imageMapper.delete(new LambdaQueryWrapper<Image>().eq(Image::getItemId, itemId));
        List<Long> images = formVO.getImages();
        for (int index = 0; index < images.size(); index++) {
            Image image = new Image();
            image.setItemId(itemId);
            image.setFileId(images.get(index));
            image.setOrderIndex(index);
            imageMapper.insert(image);
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
                .map(image -> fileClient.getFileURL(image.getFileId()).getBody())
                .toList();

        ItemVO vo = new ItemVO();
        BeanUtils.copyProperties(item, vo);
        vo.setImages(images);
        vo.setIsSeller(item.getUserId().equals(userId));

        return vo;
    }

    @Override
    @Transactional(readOnly = true)
    public ItemPageVO getItemPageVO(Long userId, ItemQueryFormVO formVO) {
        Page<Item> page = new Page<>(formVO.getPageNo(), formVO.getPageSize());
        LambdaQueryWrapper<Item> wrapper = new LambdaQueryWrapper<>();

        if (formVO.getSellerId() == 0) {
            // 获取所有在售商品
            wrapper.eq(Item::getStateCode, 2);
        } else if (formVO.getSellerId().equals(userId)) {
            // 获取自己的特定状态商品
            wrapper
                    .ne(Item::getStateCode, 0)
                    .eq(Item::getUserId, formVO.getSellerId())
                    .eq(formVO.getStateCode() != null, Item::getStateCode, formVO.getStateCode());
        } else {
            // 获取他人的在售商品
            wrapper.
                    eq(Item::getUserId, formVO.getSellerId()).
                    eq(Item::getStateCode, 1);
        }

        itemMapper.selectPage(page, wrapper);

        ItemPageVO vo = new ItemPageVO();
        BeanUtils.copyProperties(page, vo);
        vo.setRecords(page
                .getRecords()
                .stream()
                .map(item -> getItemVO(userId, item.getId()))
                .toList()
        );

        return vo;
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
    @Transactional
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

    @Override
    @Transactional
    public Boolean lockItem(Long itemId) {
        Item item = itemMapper.selectById(itemId);
        if (item.getStateCode() != 2) {
            throw new RuntimeException("商品状态异常");
        }
        item.setStateCode(4);
        updateById(item);
        return true;
    }

}
