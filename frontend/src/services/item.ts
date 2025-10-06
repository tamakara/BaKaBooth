import http from "@/services/http.ts";
import type {ItemEditFormVO, ItemVO} from "@/types/item.d.ts";

export async function createItem(formData: ItemEditFormVO): Promise<number> {
    const response = await http.post<number>('/item/create', formData);
    return response.data;
}

export async function updateItem(itemId: string, formData: ItemEditFormVO): Promise<boolean> {
    const response = await http.put<boolean>(`/item/update/${itemId}`, formData);
    return response.data;
}

export async function deleteItem(itemId: string): Promise<boolean> {
    const response = await http.delete<boolean>(`/item/delete/${itemId}`);
    return response.data;
}

export async function getItemVO(itemId: string): Promise<ItemVO> {
    const response = await http.get<ItemVO>(`/item/vo/item/${itemId}`);
    return response.data;
}

export async function getItemVOList(sellerId?: number, stateCode: number = 0, pageNo: number = 1, pageSize: number = 20): Promise<ItemVO[]> {
    const response = await http.get<ItemVO[]>(`/item/vo/items`, {
        params: { sellerId, stateCode, pageNo, pageSize }
    });
    return response.data;
}

export async function takeDownItem(itemId: string): Promise<boolean> {
    const response = await http.put<boolean>(`/item/take-down/${itemId}`);
    return response.data;
}

export async function putUpItem(itemId: string): Promise<boolean> {
    const response = await http.put<boolean>(`/item/put-up/${itemId}`);
    return response.data;
}
