import type {LoginFormDTO, RegisterFormDTO} from "@/types/user";
import axiosInstance from "@/api/index.ts";

export async function login(formData: LoginFormDTO): Promise<string> {
    const response = await axiosInstance.post<string>('/user/login', formData);
    return response.data;
}

export async function register(formData: RegisterFormDTO): Promise<string> {
    const response = await axiosInstance.post<string>('/user/register', formData);
    return response.data;
}

export async function getUserVO(userId: string): Promise<boolean> {
    const response = await axiosInstance.get<boolean>(`/vo/user`, {params: {userId}});
    return response.data;
}
