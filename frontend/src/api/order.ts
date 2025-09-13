import axiosInstance from "@/api/index.ts";

export async function createOrder(itemId: string, quantity: number): Promise<string> {
    const response = await axiosInstance.post<string>('/order/create', {itemId, quantity});
    return response.data;
}
