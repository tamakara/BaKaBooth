import type {LoginFormDTO, RegisterFormDTO, UserVO} from "@/types/user";
import http from "@/services/http.ts";

export async function login(formData: LoginFormDTO): Promise<string> {
    const response = await http.post<string>('/user/login', formData);
    return response.data;
}

export async function register(formData: RegisterFormDTO): Promise<string> {
    const response = await http.post<string>('/user/register', formData);
    return response.data;
}

export async function getUserVO(sellerId?: string): Promise<UserVO> {
    const response = await http.get<UserVO>(`/user/vo/user`, {
        params: sellerId ? { sellerId } : undefined
    });
    return response.data;
}

export async function favorite(itemId: number): Promise<void> {
    const response = await http.post<void>(`/user/favorite/${itemId}`);
    return response.data;
}

export async function unfavorite(itemId: number): Promise<void> {
    const response = await http.delete<void>(`/user/unfavorite/${itemId}`);
    return response.data;
}

export async function getFavoriteCount(itemId: number): Promise<number> {
    const response = await http.get<number>(`/user/favorite-count/${itemId}`);
    return response.data;
}

export async function isFavorite(itemId: number): Promise<boolean> {
    const response = await http.get<boolean>(`/user/is-favorite/${itemId}`);
    return response.data;
}
