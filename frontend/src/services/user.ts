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
