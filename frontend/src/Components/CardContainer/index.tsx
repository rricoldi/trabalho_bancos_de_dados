import React, { FormEvent, useContext, useEffect, useState } from 'react'
import convert from 'xml-js'
import ScrollMenu from 'react-horizontal-scrolling-menu'
import { FiSearch } from 'react-icons/fi'

import axios from 'axios'
import { Card, CardHeader, Container, TitleContainer, Title, HeaderOption, PodcastCard, PodcastContainer, Search, SearchContainer } from './styles'
import heartImg from '../../assets/Heart.png'
import headphonesImg from '../../assets/Headphones.png'
import AuthContext from '../../context/AuthContext'
import api from '../../services/api'
import { Link } from 'react-router-dom'

interface Podcast {
    id: string
    image: string
    url: string
}

const CardContainer: React.FC = () => {
    const [subscribes, setSubscribes] = useState<Podcast[]>([] as Podcast[])
    const [podcasts, setPodcasts] = useState<Podcast[]>([] as Podcast[])
    const [allPodcasts, setAllPodcasts] = useState<Podcast[]>([] as Podcast[])
    const [search, setSearch] = useState('')
    const auth = JSON.parse(localStorage.getItem('auth') || '{}')

    useEffect(() => {
        const pods = async () => {
            const result = await api.get('podcast/')
            const final = []
            for(let i = 0; i < result.data.length; i++) {
                try {
                    const a = await axios.get(`${ result.data[i].rss_feed }`, {
                        headers:{'Content-Type': 'application/x-www-form-urlencoded'}
                    })
                    const json = JSON.parse(convert.xml2json(a.data, {compact: true, spaces: 4}))
                    final.push({
                        id: result.data[i].id,
                        image: Array.isArray(json.rss.channel.image) ? Object.values(json.rss.channel.image[1].url)[0] as string : Object.values(json.rss.channel.image.url)[0] as string,
                        url: result.data[i].rss_feed
                    })
                } catch(error) {
                    console.log(error)
                }
            }

            setPodcasts(final)
            setAllPodcasts(final)
        }

        const subs = async () => {
            const result = await api.get(`usuario/pegaPodcastsInscritos/${auth.id}`)
            const final = []
            for(let i = 0; i < result.data.length; i++) {
                const a = await axios.get(`${ result.data[i].rss_feed }`, {
                    headers:{'Content-Type': 'application/x-www-form-urlencoded'}
                })
                const json = JSON.parse(convert.xml2json(a.data, {compact: true, spaces: 4}))
                final.push({
                    id: result.data[i].id,
                    image: Array.isArray(json.rss.channel.image) ? Object.values(json.rss.channel.image[1].url)[0] as string : Object.values(json.rss.channel.image.url)[0] as string,
                    url: result.data[i].rss_feed
                })
            }

            setSubscribes(final)
        }

        pods()
        if(auth.logged === true) {
            subs()
        }

    }, [])


    const MenuItem = ({ id, image, url }: Podcast) => {
        return (
            <Link to={`/podcast/${id}`} onClick={async () => {
                localStorage.setItem('auth', JSON.stringify({...auth, podcastUrl: url}))
                auth.logged && await api.post('/logAcesso/', {
                    pod_id: id,
                    usr_id: auth.id
                })
            }} >
                <PodcastCard about={image}/>
            </Link>
        )
    }

    const Menu = (list: Podcast[]): JSX.Element[] =>
        list.map(el => {
            const { id, image, url } = el

            return <MenuItem url={url} key={id} id={id} image={image} />
        })

    function handleChange(value: string,fn: React.Dispatch<React.SetStateAction<string>>) {
        fn(value)
    }

    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        if(search==='')
            return setPodcasts(allPodcasts)
        const result = await api.get(`podcast/keyword/${search}`)
        const final = []
        for(let i = 0; i < result.data.podcast.length; i++) {
            try {
                const a = await axios.get(`${ result.data.podcast[i].rss_feed }`, {
                    headers:{'Content-Type': 'application/x-www-form-urlencoded'}
                })
                const json = JSON.parse(convert.xml2json(a.data, {compact: true, spaces: 4}))
                final.push({
                    id: result.data.podcast[i].id,
                    image: Array.isArray(json.rss.channel.image) ? Object.values(json.rss.channel.image[1].url)[0] as string : Object.values(json.rss.channel.image.url)[0] as string,
                    url: result.data.podcast[i].rss_feed
                })
            } catch(error) {
                console.log(error)
            }
        }

        setPodcasts(final)
    }

    return (
        <Container>
            {
                (auth.logged === true && subscribes.length > 0) && (
                    <Card>
                        <CardHeader>
                            <TitleContainer>
                                <img src={heartImg}/>
                                <Title>
                                    <h1>Suas Inscrições</h1>
                                    <p>Você está inscrito em {subscribes.length} podcasts</p>
                                </Title>
                            </TitleContainer>
                            <HeaderOption/>
                        </CardHeader>
                        <PodcastContainer>
                            {
                                subscribes[0] && (
                                    <ScrollMenu
                                        alignCenter={false}
                                        clickWhenDrag={false}
                                        data={Menu(subscribes)}
                                        dragging={true}
                                        transition={+0.3}
                                        translate={0}
                                        wheel={true}
                                        wrapperStyle={{
                                            overflow: 'hidden'
                                        }}
                                    />
                                )
                            }
                        </PodcastContainer>
                    </Card>
                )
            }
            <Card>
                <CardHeader>
                    <TitleContainer>
                        <img src={headphonesImg}/>
                        <Title>
                            <h1>Feed de Podcasts</h1>
                            <p>{podcasts.length} podcasts cadastrados</p>
                        </Title>
                    </TitleContainer>
                    <SearchContainer>
                        <Search onSubmit={(event) => handleSubmit(event)}>
                            <button type='submit'>
                                <FiSearch color='#fff' size='2vw'></FiSearch>
                            </button>
                            <input value={search} onChange={ (event) => handleChange(event.target.value, setSearch) } placeholder='Procurar Podcast' />
                        </Search>
                    </SearchContainer>
                    <HeaderOption/>
                </CardHeader>
                {
                    podcasts[0] && (
                        <ScrollMenu
                            alignCenter={false}
                            clickWhenDrag={false}
                            data={Menu(podcasts)}
                            dragging={true}
                            transition={+0.3}
                            translate={0}
                            wheel={true}
                            wrapperStyle={{
                                overflow: 'hidden'
                            }}
                        />
                    )
                }
            </Card>
        </Container>
    )

}

export default CardContainer
