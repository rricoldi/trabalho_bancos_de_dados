import React, { FormEvent, useContext, useState } from 'react'
import { useHistory } from 'react-router-dom'

import Header from '../../Components/Header'
import AuthContext from '../../context/AuthContext'
import api from '../../services/api'
import { Card, Form } from './styles'

const UserLogin: React.FC = () => {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')

    const history = useHistory()

    const auth = useContext(AuthContext)

    function handleChange(value: string,fn: React.Dispatch<React.SetStateAction<string>>) {
        fn(value)
    }

    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        await api.post('usuario/login', {
            nome: null,
            email,
            sexo: null,
            idade: 0,
            senha: password,
            pais: null
        }).then((data) => {
            if(data.data.code === '400') {
                alert(data.data.status)
            } else {
                console.log(data)
                auth.setData({
                    ...auth,
                    id: data.data.id,
                    name: data.data.nome,
                    sex: data.data.sexo,
                    logged: true
                })
                history.push('/')
            }
        })
    }

    return (
        <>
            <Header/>
            <Card>
                <Form onSubmit={(event) => handleSubmit(event)}>
                    <div>
                        <h1>E-mail</h1>
                        <input required placeholder='nome@email.com' value={email} onChange={ (event) => handleChange(event.target.value, setEmail) }></input>

                        <h1>Senha</h1>
                        <input type="password" onChange={ (event) => handleChange(event.target.value, setPassword) } pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters" required />
                    </div>

                    <button type='submit'>
                        Login
                    </button>
                </Form>
            </Card>
        </>
    )
}

export default UserLogin
