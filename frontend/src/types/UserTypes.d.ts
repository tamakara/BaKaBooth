export interface LoginFormDTO {
    phone: string
    password: string
}

export interface RegisterFormDTO {
    phone: string
    password: string
    confirmPassword?: string
}

export interface UserSimpleInfoVO {
    id: number
    username: string
    avatarUrl: string
}

export interface ShopManageVO {
    state: string
    description: string
    followers: number
}

export interface SellerVO {
    username: string
    state: string
    description: string
    followers: number
    avatarUrl: string
}
