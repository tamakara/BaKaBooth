import type {LoginFormDTO, RegisterFormDTO, ShopManagePageVO, UserSimpleInfoVO} from "@/types/UserTypes";
import axiosInstance from "@/api/index.ts";
import type {SellerUserVO} from "@/types/UserTypes";

export async function login(data: LoginFormDTO): Promise<string> {
    const response = await axiosInstance.post<string>('/user/login', data);
    return response.data;
}

export async function register(data:RegisterFormDTO): Promise<string> {
    const response = await axiosInstance.post<string>('/user/register', data);
    return response.data;
}

export async function getUserSimpleInfo(): Promise<UserSimpleInfoVO> {
    const response = await axiosInstance.get<UserSimpleInfoVO>('/user/vo/simple-info');
    return response.data;
}

export async function getShopManagePageVO(): Promise<ShopManagePageVO> {
    const response = await axiosInstance.get<ShopManagePageVO>('/user/vo/shop-manage-page');
    return response.data;
}

export async function getSellerUserVO(userId: string): Promise<SellerUserVO> {
    const response = await axiosInstance.get<SellerUserVO>(`/user/vo/seller-user/${userId}`);
    return response.data;
}
