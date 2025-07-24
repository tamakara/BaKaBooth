import type {LoginForm, UserStatus} from "@/types/user";
import axiosInstance from "@/api/index.ts";

export async function login(data: LoginForm): Promise<UserStatus> {
    const response = await axiosInstance.post<UserStatus>('/user/login', data);
    return response.data;
}