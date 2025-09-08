import axiosInstance from "@/api/index.ts";
import type {ItemEditFormVO, ItemManageVO, ItemVO} from "@/types/ItemTypes";

export async function createItem(): Promise<number> {
    const response = await axiosInstance.post<number>('/item/create');
    return response.data;
}

export async function updateItem(itemId: string, formData: ItemEditFormVO): Promise<void> {
    const response = await axiosInstance.put<void>(`/item/update/${itemId}`, formData);
    return response.data;
}


export async function getItemManageVO(state: string): Promise<Array<ItemManageVO>> {
    const response = await axiosInstance.get<Array<ItemManageVO>>('/item/vo/item-manage', {params: {state}});
    return response.data;
}

export async function getItemEditFormVO(itemId: string): Promise<ItemEditFormVO> {
    const response = await axiosInstance.get<ItemEditFormVO>(`/item/vo/item-edit-form/${itemId}`);
    return response.data;
}

export async function getItemVO(itemId: string): Promise<ItemVO> {
    const response = await axiosInstance.get<ItemVO>(`/item/vo/item/${itemId}`);
    return response.data;
}

