import React, { createContext } from 'react'

export interface AuthContextData {
    name: string
    sex: 'male' | 'female' | 'other'
    logged: boolean
    id: string
    podcastUrl: string
    setData: (data: AuthContextData) => void
}

const AuthContext = createContext<AuthContextData>({} as AuthContextData)

export default AuthContext
