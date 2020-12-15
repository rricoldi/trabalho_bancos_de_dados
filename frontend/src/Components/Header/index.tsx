import React, { useContext } from 'react'

import { Button, Container, User, Title } from './styles'
import micImage from '../../assets/Mic.png'
import AuthContext from '../../context/AuthContext'
import { Link } from 'react-router-dom'

const Header: React.FC = () => {

    const auth = useContext(AuthContext)

    if(auth.logged === true) {
        return (
            <Container>
                <Link to='/'>
                    <Title>
                        <img src={micImage} />
                        <h1>Pode</h1>
                    </Title>
                </Link>
                <Button>
                    <Link to='/podcast/register'>Cadastrar Podcast</Link>
                    <User>
                        {
                            auth.sex === 'male' && <p>Bem Vindo de volta</p>
                        }
                        {
                            auth.sex === 'female' && <p>Bem Vinda de volta</p>
                        }
                        {
                            auth.sex === 'other' && <p>Bem Vinde de volta</p>
                        }
                        <a href="#">{auth.name}</a>
                    </User>

                    <a href="/" onClick={() => { auth.setData({ ...auth, logged: false }) }}>Sair</a>
                </Button>
            </Container>
        )
    } else {
        return (
            <Container>
                <Link to='/'>
                    <Title>
                        <img src={micImage} />
                        <h1>Pode</h1>
                    </Title>
                </Link>
                <Button>
                    <a></a>
                    <a></a>
                    <Link to='/user/register'>Cadastrar</Link>
                    <Link to='/user/login'>Entrar</Link>
                </Button>
            </Container>
        )
    }
}

export default Header
