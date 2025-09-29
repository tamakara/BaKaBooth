import axiosInstance from "@/api/index.ts";
import type {ItemEditFormVO, ItemVO} from "@/types/item.d.ts";
import type {FileVO} from "@/types/file";

export async function createItem(formData: ItemEditFormVO): Promise<number> {
    const response = await axiosInstance.post<number>('/item/create', formData);
    return response.data;
}

export async function updateItem(itemId: string, formData: ItemEditFormVO): Promise<number> {
    const response = await axiosInstance.put<number>(`/item/update/${itemId}`, formData);
    return response.data;
}

export async function deleteItem(itemId: string): Promise<number> {
    const response = await axiosInstance.delete<number>(`/item/delete/${itemId}`);
    return response.data;
}

export async function getItemVO(itemId: string): Promise<ItemVO> {
    const response = await axiosInstance.get<ItemVO>(`/item/vo/item/${itemId}`,);
    return response.data;
}

export async function getItemVOList(sellerId: number, stateCode: number, pageNo: number, pageSize: number): Promise<ItemVO> {
    const response = await axiosInstance.get<ItemVO>(`/item/vo/items`, {
        params: {sellerId, stateCode, pageNo, pageSize}
    });
    return response.data;
}

export async function takeDownItem(itemId: string): Promise<number> {
    const response = await axiosInstance.put<number>(`/item/take-down/${itemId}`);
    return response.data;
}

export async function putUpItem(itemId: string): Promise<number> {
    const response = await axiosInstance.put<number>(`/item/put-up/${itemId}`);
    return response.data;
}

export async function favorite(itemId: number): Promise<void> {
    const response =
        await axiosInstance.post<void>(`/item/favorite/${itemId}`);
    return response.data;
}

export async function unfavorite(itemId: number): Promise<void> {
    const response =
        await axiosInstance.delete<void>(`/item/unfavorite/${itemId}`);
    return response.data;
}

export async function getFavoriteCount(itemId: number): Promise<number> {
    const response =
        await axiosInstance.get<number>(`/item/favorite-count/${itemId}`);
    return response.data;
}

export async function isFavorite(itemId: number): Promise<boolean> {
    const response = await axiosInstance.get<boolean>(`/item/is-favorite/${itemId}`);
    return response.data;
}


