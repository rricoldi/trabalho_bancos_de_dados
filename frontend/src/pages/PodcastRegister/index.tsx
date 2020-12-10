import React from 'react'

import Header from '../../Components/Header'

import { Card, Form } from './styles'

const Register: React.FC = () => {
    return (
        <>
            <Header logado={true} sexo='male' nome='Renan'/>
            <Card>
                <Form>
                    <div>
                        <h1>Nome do Podcast *</h1>
                        <input placeholder='Podcast do Jão'></input>
                    </div>
                    <div>
                        <h1>Site do Podcast *</h1>
                        <input placeholder='https://podcastdojao.com.br'></input>
                    </div>
                    <div>
                        <h1>Feed RRS *</h1>
                        <input placeholder='https://podcastdojao.com.br/feed/podcast'></input>
                    </div>
                    <div>
                        <h1>E-mail de contato</h1>
                        <input placeholder='podcast@mail.com'></input>
                    </div>
                    <button>
                        Cadastrar Podcast
                    </button>
                </Form>
            </Card>
        </>
    )
}

export default Register
