import React, { FormEvent, useContext, useEffect, useState } from 'react'
import { FiRss, FiGlobe, FiMail, FiBarChart2, FiPlayCircle, FiPauseCircle, FiHeart, FiSend } from 'react-icons/fi'
import convert from 'xml-js'
import axios from 'axios'
import StarPicker from 'react-star-picker'

import { About, Buttons, Card, Comment, CommentCard, Container, EpisodeCard, EpisodeImageCard, EpisodeInfo, EpisodeStats, Info, Message, PodcastCard, SendContainer, Stats, Subscribe } from './styles'
import api from '../../services/api'
import Header from '../../Components/Header'
import CommentImg from '../../assets/comments.png'
import StarsImg from '../../assets/stars.png'
import HeartImg from '../../assets/Heart.png'
import { useRouteMatch } from 'react-router-dom'
import AuthContext from '../../context/AuthContext'

interface Props {
    podcastUrl: string
    podcastId: string
    userId: string
}

interface Podcast {
    name: string
    description: string
    imageUrl: string
    episodes: Episode[]
    stars?: number
    userStars?: number
    comments?: number
    subscribed?: boolean
    site?: string
    email?: string
    feed?: string
}

interface Episode {
    id: string
    name: string
    image?: string
    likes?: number
    comments?:number
}

interface Params {
    id: string
}

const Podcast: React.FC<Props> = () => {
    const [podcast, setPodcast] = useState<Podcast>()
    const [showComment, setShowComment] = useState(false)
    const [comments, setComments] = useState<string[]>([])

    const [message, setMessage] = useState('')

    const { podcastUrl, id:userId, logged } = useContext(AuthContext)

    const { params } = useRouteMatch<Params>()
    const podcastId = params.id

    useEffect(() => {
        const data = async () => {
            const result = await axios.get(`${ podcastUrl }`, {
                headers:{'Content-Type': 'application/x-www-form-urlencoded'}
            })
            const json = JSON.parse(convert.xml2json(result.data, {compact: true, spaces: 4}))
            if(logged) {
                const additional = await api.get(`podcast/statistics/${podcastId}/${userId}`)
                console.log(additional)

                setPodcast({
                    name: Object.values(json.rss.channel.title)[0] as string,
                    description: Object.values(json.rss.channel.description)[0] as string,
                    imageUrl: Array.isArray(json.rss.channel.image) ? Object.values(json.rss.channel.image[1].url)[0] as string : Object.values(json.rss.channel.image.url)[0] as string,
                    episodes: json.rss.channel.item.map( (episode: any) => {
                        return {
                            id: Object.values(episode.guid)[0],
                            name: Object.values(episode.title)[0],
                            image: episode['itunes:image'] ? episode['itunes:image']._attributes.href : Array.isArray(json.rss.channel.image) ? Object.values(json.rss.channel.image[1].url)[0] as string : Object.values(json.rss.channel.image.url)[0] as string,
                        }
                    }),
                    stars: additional.data.estrelas,
                    userStars: additional.data.estrelas_do_usuario,
                    comments: additional.data.comentarios,
                    subscribed: additional.data.inscrito,
                    site: additional.data.site,
                    email: additional.data.email,
                    feed: additional.data.feed_rss,
                })

            } else {
                setPodcast({
                    name: Object.values(json.rss.channel.title)[0] as string,
                    description: Object.values(json.rss.channel.description)[0] as string,
                    imageUrl: Array.isArray(json.rss.channel.image) ? Object.values(json.rss.channel.image[1].url)[0] as string : Object.values(json.rss.channel.image.url)[0] as string,
                    episodes: json.rss.channel.item.map( (episode: any) => {
                        return {
                            id: Object.values(episode.guid)[0],
                            name: Object.values(episode.title)[0],
                            image: episode['itunes:image'] ? episode['itunes:image']._attributes.href : Array.isArray(json.rss.channel.image) ? Object.values(json.rss.channel.image[1].url)[0] as string : Object.values(json.rss.channel.image.url)[0] as string,
                        }
                    })
                })
            }

            console.log(podcast)
        }
        data()
    }, [podcastUrl, podcast?.userStars])

    const handleSubscribe = async () => {
        console.log(podcast)
        if(podcast?.subscribed) {
            await api.delete(`inscrever/${userId}/${podcastId}`)
            setPodcast({
                ...podcast,
                subscribed: false
            })
        } else if(podcast?.subscribed == false) {
            await api.post('inscrever/', {
                usr_id: userId,
                pod_id: podcastId,
                classificacao: 0
            })
            setPodcast({
                ...podcast,
                subscribed: true
            })
        }
    }

    const handleStars = async (value: number) => {
        if(podcast){
            setPodcast({
                ...podcast,
                userStars: value
            })

            await api.put(`inscrever/${userId}/${podcastId}`, {
                usr_id: userId,
                pod_id: podcastId,
                classificacao: value
            })
        }
    }

    const handleShowComment = async() => {
        if(!showComment && comments.length == 0) {
            const comms = await api.get(`comentario/${podcastId}`)
            setComments(comms.data.comments.map( (comment:any) => comment.comentario ))
        }
        setShowComment(!showComment)
    }

    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        await api.post('comentario/', {
            pod_id: podcastId,
            usr_id: userId,
            comentario: message
        })

        const newCom = comments
        newCom.push(message)
        setComments(newCom)

        setMessage('')
        if(podcast) {
            setPodcast({
                ...podcast,
                comments: comments.length
            })
        }
    }

    return (
        <>
            <Header/>
            <Container>
                <Card>
                    <Info>
                        <PodcastCard about={ podcast?.imageUrl }/>
                        <About>
                            <h1>{ podcast?.name }</h1>
                            <p>{ podcast?.description }</p>
                            <Stats>
                                <img src={StarsImg} />
                                <h1>{ podcast?.stars ? podcast?.stars : 0 }</h1>
                                <img style={{cursor: 'pointer'}} onClick={handleShowComment} src={CommentImg} />
                                <h1 style={{cursor: 'pointer'}} onClick={handleShowComment} >{`${ podcast?.comments ? podcast?.comments : 0 } comentários`}</h1>
                            </Stats>
                            <Buttons>
                                <a href={podcast?.site}><FiGlobe color='#fff' size='1.9vw' /></a>
                                {
                                    podcast?.email && <a href={`mailto:${podcast?.email}`}><FiMail color='#fff' size='1.9vw' /></a>
                                }
                                <a href={podcast?.feed}><FiRss color='#fff' size='1.9vw' /></a>
                                <a href='/'><FiBarChart2 color='#fff' size='1.9vw' /></a>
                                {
                                    logged && <StarPicker onChange={(value) => handleStars(value ? value : 0)} value={podcast?.userStars ? podcast.userStars : 0} />
                                }
                            </Buttons>
                        </About>
                        {
                            logged && (
                                <Subscribe onClick={handleSubscribe}>
                                    { podcast?.subscribed ? 'Inscrito' : 'Inscrever' }
                                </Subscribe>
                            )
                        }
                    </Info>
                    {
                        showComment ? (
                            <CommentCard>
                                <div>
                                    {
                                        comments.map(
                                            comment => {
                                                return (
                                                    <Comment key={comment}>
                                                        <p>{ comment }</p>
                                                    </Comment>
                                                )
                                            }
                                        )
                                    }
                                </div>
                                <SendContainer onSubmit={(event) => handleSubmit(event)}>
                                    <Message placeholder='Digite sua mensagem' value={message} onChange={(event) => setMessage(event.target.value)} />
                                    <button type='submit'>
                                        <FiSend color='#fff' size='1.9vw' />
                                    </button>
                                </SendContainer>
                            </CommentCard>
                        ) : (<></>)
                    }
                    {
                        podcast?.episodes.map( episode => {
                            return (
                                <EpisodeCard key={episode.id}>
                                    <EpisodeImageCard about={ episode?.image } />
                                    <EpisodeInfo>
                                        <h1>{ episode.name }</h1>
                                        <EpisodeStats>
                                            <img src={HeartImg} />
                                            <h1>{ episode?.likes ? episode?.likes : 0 }</h1>
                                        </EpisodeStats>
                                    </EpisodeInfo>
                                </EpisodeCard>
                            )
                        })
                    }
                </Card>
            </Container>
        </>
    )
}

export default Podcast
