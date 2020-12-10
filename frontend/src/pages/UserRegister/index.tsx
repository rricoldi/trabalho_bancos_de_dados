import React from 'react'
import Header from '../../Components/Header'

import { Card, Form } from './styles'

const UserRegister: React.FC = () => {
    return (
        <>
            <Header logado={true} sexo='male' nome='Renan'/>
            <Card>
                <Form>
                    <div>
                        <h1>Nome</h1>
                        <input placeholder='Jão'></input>
                        <h1>Idade</h1>
                        <input placeholder='25'></input>
                    </div>
                    <div>
                        <h1>E-mail</h1>
                        <input placeholder='nome@email.com'></input>
                        <h1>Sexo</h1>
                        <input placeholder='Homem'></input>
                    </div>
                    <div>
                        <h1>Senha</h1>
                        <input placeholder='********'></input>
                        <h1>País</h1>
                        <input placeholder='Brazil'></input>
                    </div>
                    <button>
                        Cadastrar Usuário
                    </button>
                </Form>
            </Card>
        </>
    )
}

export default UserRegister
