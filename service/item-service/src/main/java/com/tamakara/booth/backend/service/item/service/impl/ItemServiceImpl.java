package com.tamakara.booth.backend.service.item.service.impl;

import com.tamakara.booth.backend.common.client.FileClient;
import com.tamakara.booth.backend.service.item.domain.entity.Image;
import com.tamakara.booth.backend.service.item.domain.entity.Item;
import com.tamakara.booth.backend.service.item.domain.pojo.ItemState;
import com.tamakara.booth.backend.service.item.domain.vo.ItemEditFormVO;
import com.tamakara.booth.backend.service.item.domain.vo.ItemPageVO;
import com.tamakara.booth.backend.service.item.domain.vo.ItemQueryFormVO;
import com.tamakara.booth.backend.service.item.domain.vo.ItemVO;
import com.tamakara.booth.backend.service.item.mapper.ImageMapper;
import com.tamakara.booth.backend.service.item.mapper.ItemMapper;
import com.tamakara.booth.backend.service.item.service.ItemService;
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

        Long itemId = item.getId();

        List<Long> images = formVO.getImages();
        for (int index = 0; index < images.size(); index++) {
            Image image = new Image();
            image.setItemId(itemId);
            image.setFileId(images.get(index));
            image.setOrderIndex(index);
            imageMapper.insert(image);
        }

        return itemId;
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
            wrapper.eq(Item::getItemState, ItemState.ON_SALE);
        } else if (formVO.getSellerId().equals(userId)) {
            // 获取自己的特定状态商品
            wrapper
                    .ne(Item::getItemState, ItemState.DELETED)
                    .eq(Item::getUserId, formVO.getSellerId())
                    .eq(formVO.getItemState() != ItemState.ALL, Item::getItemState, formVO.getItemState());
        } else {
            // 获取他人的在售商品
            wrapper.
                    eq(Item::getUserId, formVO.getSellerId()).
                    eq(Item::getItemState, ItemState.ON_SALE);
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
        if (item.getItemState() == ItemState.OFF_SHELF) {
            return true;
        }
        if (item.getItemState() != ItemState.ON_SALE) {
            throw new RuntimeException("商品状态异常");
        }

        lambdaUpdate()
                .eq(Item::getUserId, userId)
                .set(Item::getItemState, ItemState.OFF_SHELF);

        return true;
    }

    @Override
    @Transactional
    public Boolean putUpItem(Long userId, Long itemId) {
        Item item = itemMapper.selectById(itemId);
        if (!userId.equals(item.getUserId())) {
            throw new RuntimeException("没有权限");
        }
        if (item.getItemState() == ItemState.ON_SALE) {
            return true;
        }
        if (item.getItemState() != ItemState.OFF_SHELF) {
            throw new RuntimeException("商品状态异常");
        }

        lambdaUpdate()
                .eq(Item::getUserId, userId)
                .set(Item::getItemState, ItemState.ON_SALE);

        return null;
    }

    @Override
    @Transactional
    public Boolean lockItem(Long itemId) {
        Item item = itemMapper.selectById(itemId);
        if (item.getItemState() != ItemState.ON_SALE) {
            throw new RuntimeException("商品状态异常");
        }
        item.setItemState(ItemState.RESERVED);
        updateById(item);
        return true;
    }

}
