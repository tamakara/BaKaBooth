import axiosInstance from "@/api/index.ts";
import type {ItemManageVO} from "@/types/ItemTypes";

export async function createItem(): Promise<number> {
    const response = await axiosInstance.post<number>('/item/create');
    return response.data;
}

export async function getItemManageVO(): Promise<Array<ItemManageVO>> {
    const response = await axiosInstance.get<Array<ItemManageVO>>('/item/vo/item-manage');
    return response.data;
}