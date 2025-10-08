import http from "@/services/http.ts";
import type {OrderVO} from "@/types/order";

export async function createOrder(itemId: number): Promise<number> {
    const response = await http.post<number>(`/order/create/${itemId}`);
    return response.data;
}

export async function getOrderVO(orderId: number): Promise<OrderVO> {
    const response = await http.get<OrderVO>(`/order/vo/order/${orderId}`);
    return response.data;
}

