import axiosInstance from "@/api/index.ts";

export async function createOrder(variationId: string): Promise<string> {
    const response = await axiosInstance.post<string>('/order/create', {variationId});
    return response.data;
}
