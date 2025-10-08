export interface ItemVO {
    id: number
    userId: number
    isSeller: boolean
    stateCode: number// 0-已删除, 1-草稿, 2-在售, 3-已下架, 4-锁定, 5-已售出
    deliveryMethodCode: number// 0-无需邮寄, 1-包邮, 2-固定邮费

    price: number
    postage: string

    name: string
    description: string

    favorites: number

    createdAt: string
    updatedAt: string

    images: string[]
}

export interface ItemEditFormVO {
    stateCode: number// 0-已删除, 1-草稿, 2-在售, 3-已下架, 4-锁定, 5-已售出
    deliveryMethodCode: number// 0-无需邮寄, 1-包邮, 2-固定邮费

    name: string
    description: string

    price: number
    postage: number

    images: number[]
}

export interface ItemQueryFormVO {
    sellerId?: number
    stateCode?: number
    pageNo?: number
    pageSize?: number
    keyword?: string
    isFavorite?: boolean
}

export interface ItemPageVO {
    records: ItemVO[]
    total: number
    size: number
    current: number
}

