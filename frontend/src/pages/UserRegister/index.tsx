import React, { FormEvent, useContext, useState } from 'react'
import { useHistory } from 'react-router-dom'

import Header from '../../Components/Header'
import AuthContext from '../../context/AuthContext'
import api from '../../services/api'
import { Card, Form } from './styles'

const UserRegister: React.FC = () => {
    const [name, setName] = useState('')
    const [age, setAge] = useState('')
    const [email, setEmail] = useState('')
    const [sex, setSex] = useState('male')
    const [password, setPassword] = useState('')
    const [country, setCountry] = useState('')

    const history = useHistory()

    const auth = useContext(AuthContext)

    function handleChange(value: string,fn: React.Dispatch<React.SetStateAction<string>>) {
        fn(value)
    }

    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        await api.post('usuario/', {
            nome: name,
            email,
            sexo: sex,
            idade: Number(age),
            senha: password,
            pais: country
        }).then((data) => {
            auth.setData({
                ...auth,
                id: data.data.created.id,
                name: data.data.created.nome,
                sex: data.data.created.sexo,
                logged: true
            })
            history.push('/')
        })
    }

    return (
        <>
            <Header/>
            <Card>
                <Form onSubmit={(event) => handleSubmit(event)}>
                    <div>
                        <h1>Nome</h1>
                        <input required placeholder='Jão' value={name} onChange={ (event) => handleChange(event.target.value, setName) }></input>
                        <h1>Idade</h1>
                        <input required placeholder='25' value={age} onChange={ (event) => handleChange(event.target.value, setAge) }></input>
                    </div>
                    <div>
                        <h1>E-mail</h1>
                        <input required placeholder='nome@email.com' value={email} onChange={ (event) => handleChange(event.target.value, setEmail) }></input>
                        <h1>Sexo</h1>
                        <select required onChange={ (event) => handleChange(event.target.value, setSex) }>
                            <option value='male'>Homem</option>
                            <option value='female'>Mulher</option>
                            <option value='other'>Outro</option>
                        </select>
                    </div>
                    <div>
                        <h1>Senha</h1>
                        <input type="password" onChange={ (event) => handleChange(event.target.value, setPassword) } pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters" required />
                        <h1>País</h1>
                        <input required placeholder='Brazil' value={country} onChange={ (event) => handleChange(event.target.value, setCountry) }></input>
                    </div>
                    <button type='submit'>
                        Cadastrar Usuário
                    </button>
                </Form>
            </Card>
        </>
    )
}

export default UserRegister
