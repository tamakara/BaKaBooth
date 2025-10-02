import http from "@/services/http.ts";
import type {ItemEditFormVO, ItemVO} from "@/types/item.d.ts";

export async function createItem(formData: ItemEditFormVO): Promise<number> {
    const response = await http.post<number>('/item/create', formData);
    return response.data;
}

export async function updateItem(itemId: string, formData: ItemEditFormVO): Promise<number> {
    const response = await http.put<number>(`/item/update/${itemId}`, formData);
    return response.data;
}

export async function deleteItem(itemId: string): Promise<number> {
    const response = await http.delete<number>(`/item/delete/${itemId}`);
    return response.data;
}

export async function getItemVO(itemId: string): Promise<ItemVO> {
    const response = await http.get<ItemVO>(`/item/vo/item/${itemId}`,);
    return response.data;
}

export async function getItemVOList(sellerId: number, stateCode: number, pageNo: number, pageSize: number): Promise<ItemVO> {
    const response = await http.get<ItemVO>(`/item/vo/items`, {
        params: {sellerId, stateCode, pageNo, pageSize}
    });
    return response.data;
}

export async function takeDownItem(itemId: string): Promise<number> {
    const response = await http.put<number>(`/item/take-down/${itemId}`);
    return response.data;
}

export async function putUpItem(itemId: string): Promise<number> {
    const response = await http.put<number>(`/item/put-up/${itemId}`);
    return response.data;
}

export async function favorite(itemId: number): Promise<void> {
    const response =
        await http.post<void>(`/item/favorite/${itemId}`);
    return response.data;
}

export async function unfavorite(itemId: number): Promise<void> {
    const response =
        await http.delete<void>(`/item/unfavorite/${itemId}`);
    return response.data;
}

export async function getFavoriteCount(itemId: number): Promise<number> {
    const response =
        await http.get<number>(`/item/favorite-count/${itemId}`);
    return response.data;
}

export async function isFavorite(itemId: number): Promise<boolean> {
    const response = await http.get<boolean>(`/item/is-favorite/${itemId}`);
    return response.data;
}
