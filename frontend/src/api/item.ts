import axiosInstance from "@/api/index.ts";

export async function createItem(): Promise<number> {
    const response = await axiosInstance.post<number>('/item/create');
    return response.data;
}