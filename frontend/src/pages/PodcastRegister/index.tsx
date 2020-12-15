import React, { FormEvent, useState }  from 'react'

import Header from '../../Components/Header'
import { Card, Form } from './styles'
import api from '../../services/api'
import { useHistory } from 'react-router-dom'

const Register: React.FC = () => {
    const [name, setName] = useState('')
    const [site, setSite] = useState('')
    const [email, setEmail] = useState('')
    const [feed, setFeed] = useState('')

    const history = useHistory()

    function handleChange(value: string,fn: React.Dispatch<React.SetStateAction<string>>) {
        fn(value)
    }

    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        await api.post('podcast/', {
            nome: name,
            email: email === '' ? null : email,
            site,
            rss_feed: feed
        }).then((data) => {
            if(data.data.code == '201') {
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
                        <h1>Nome do Podcast *</h1>
                        <input placeholder='Podcast do Jão' value={name} onChange={ (event) => handleChange(event.target.value, setName) }></input>
                    </div>
                    <div>
                        <h1>Site do Podcast *</h1>
                        <input placeholder='https://podcastdojao.com.br' value={site} onChange={ (event) => handleChange(event.target.value, setSite) }></input>
                    </div>
                    <div>
                        <h1>Feed RRS *</h1>
                        <input placeholder='https://podcastdojao.com.br/feed/podcast' value={feed} onChange={ (event) => handleChange(event.target.value, setFeed) }></input>
                    </div>
                    <div>
                        <h1>E-mail de contato</h1>
                        <input placeholder='podcast@mail.com' value={email} onChange={ (event) => handleChange(event.target.value, setEmail) }></input>
                    </div>
                    <button type='submit'>
                        Cadastrar Podcast
                    </button>
                </Form>
            </Card>
        </>
    )
}

export default Register
