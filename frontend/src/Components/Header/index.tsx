import React from 'react'

import { Button, Container, User, Title } from './styles'
import micImage from '../../assets/Mic.png'

interface Props {
    logado: boolean
    sexo: 'male' | 'female' | 'other'
    nome: string
}

const Header: React.FC<Props> = (props) => {
    if(props.logado === true) {
        return (
            <Container>
                <a href="/" style={{textDecoration: 'none'}}>
                    <Title>
                        <img src={micImage} />
                        <h1>Pode</h1>
                    </Title>
                </a>
                <Button>
                    <a href="#">Cadastrar Podcast</a>
                    <User>
                        {
                            props.sexo === 'male' && <p>Bem Vindo de volta</p>
                        }
                        {
                            props.sexo === 'female' && <p>Bem Vinda de volta</p>
                        }
                        {
                            props.sexo === 'other' && <p>Bem Vinde de volta</p>
                        }
                        <a href="#">{props.nome}</a>
                    </User>

                    <a href="#">Sair</a>
                </Button>
            </Container>
        )
    } else {
        return (
            <Container>
                <a href="/" style={{textDecoration: 'none'}}>
                    <Title>
                        <img src={micImage} />
                        <h1>Pode</h1>
                    </Title>
                </a>
                <Button>Entrar</Button>
            </Container>
        )
    }
}

export default Header
