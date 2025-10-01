import http from "@/services/http.ts";

export async function createOrder(variationId: string): Promise<string> {
    const response = await http.post<string>('/order/create', {variationId});
    return response.data;
}
