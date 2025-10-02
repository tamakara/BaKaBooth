import http from "@/services/http.ts";

export async function createOrder(itemId: number): Promise<number> {
    const response = await http.post<number>(`/order/create/${itemId}`);
    return response.data;
}

export async function getOrderVO(orderId: string): Promise<any> {
    const response = await http.get<any>(`/order/vo/order/${orderId}`);
    return response.data;
}
