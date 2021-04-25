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
    const [tag, setTag] = useState('')

    const history = useHistory()

    function handleChange(value: string,fn: React.Dispatch<React.SetStateAction<string>>) {
        fn(value)
    }

    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
        console.log({
            nome: name,
            email: email === '' ? null : email,
            site,
            rss_feed: feed,
            tag
        })
        event.preventDefault()
        try {
            const response = await api.post('podcast/', {
                nome: name,
                email: email === '' ? null : email,
                site,
                rss_feed: feed
            })

            if(response.data.code == '201') {
                await api.post('/tag/', {
                    pod_id: response.data.created.id,
                    tag
                })

                history.push('/')
            } else {
                alert(response.data.status)
            }
        } catch(error) {
            alert(error.message)
        }
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
                    <div>
                        <h1>Tag</h1>
                        <select value={tag} onChange={ (event) => setTag(event.target.value) }>
                            <option value="Cinema">Cinema</option>
                            <option value="Dia a Dia">Dia a Dia</option>
                            <option value="Tecnologia">Tecnologia</option>
                            <option value="Comédia">Comédia</option>
                            <option value="Prgramação">Prgramação</option>
                            <option value="Terror">Terror</option>
                            <option value="Programação">Programação</option>
                            <option value="Educação">Educação</option>
                        </select>
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
