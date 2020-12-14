import React, { useState, useEffect, useContext } from 'react'
import { FiPlayCircle, FiPauseCircle, FiHeart} from 'react-icons/fi'

import { EpisodeCard, EpisodeImageCard, EpisodeInfo, EpisodeStats, Buttons } from './styles'
import HeartImg from '../../assets/Heart.png'
import AuthContext from '../../context/AuthContext'
import api from '../../services/api'

interface Props {
    id: string
    podcastId: string
    image: string
    name: string
    audioUrl: string
    likes: number
    likd: boolean
}

const useAudio = (url: string) => {
    const [audio] = useState(new Audio(url))
    const [playing, setPlaying] = useState<boolean>(false)

    const toggle = () => {
        setPlaying(!playing)

    }

    useEffect(() => {
        playing ? audio.play() : audio.pause()
    },
    [playing]
    )

    useEffect(() => {
        audio.addEventListener('ended', () => setPlaying(false))
        return () => {
            audio.removeEventListener('ended', () => setPlaying(false))
        }
    }, [])

    return [playing, toggle]
}

const Episode: React.FC<Props> = ({id, podcastId, image, name, audioUrl, likes, likd}: Props) => {

    const [playing, toggle] = useAudio(audioUrl)
    const { logged, id:userId } = useContext(AuthContext)

    const [liked, setLiked] = useState(likd)

    const handleLike = async () => {

        setLiked(!liked)

        await api.post('classificacao/', {
            usr_id: userId,
            pod_id: podcastId,
            ep_id:  id
        })
    }

    return (
        <EpisodeCard key={id}>
            <div style={{display: 'flex'}}>
                <EpisodeImageCard about={ image } />
                <EpisodeInfo>
                    <h1>{ name }</h1>
                    {
                        logged ? (
                            <EpisodeStats>
                                <img src={HeartImg} />
                                <h1>{ likes }</h1>
                            </EpisodeStats>
                        ) : (
                            <div></div>
                        )
                    }
                </EpisodeInfo>
            </div>
            <Buttons>
                <button onClick={toggle as ((event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => void) | undefined}>
                    {
                        playing ? (
                            <FiPauseCircle size='1.9vw' color='#fff' ></FiPauseCircle>
                        ) : (
                            <FiPlayCircle size='1.9vw' color='#fff' ></FiPlayCircle>
                        )
                    }
                </button>
                {
                    logged && (
                        liked ? (
                            <FiHeart onClick={() => handleLike()} size='1.9vw' color='#fff' fill='#fff' ></FiHeart>
                        ) : (
                            <FiHeart onClick={() => handleLike()} size='1.9vw' color='#fff' ></FiHeart>
                        )
                    )
                }
            </Buttons>
        </EpisodeCard>
    )
}

export default Episode
