export interface LoginFormDTO {
    phone: string
    password: string
}

export interface RegisterFormDTO {
    phone: string
    nickname: string
    username: string
    password: string
    confirmPassword?: string
}

export interface UserSimpleInfoVO {
    id: number
    nickname: string
    avatarUrl: string
}