import React from 'react'
import { FiRss, FiGlobe, FiMail, FiBarChart2, FiPlayCircle, FiPauseCircle, FiHeart } from 'react-icons/fi'

import { About, Buttons, Card, Container, EpisodeCard, EpisodeImageCard, EpisodeInfo, EpisodeStats, Info, PodcastCard, Stats, Subscribe } from './styles'
import Header from '../../Components/Header'
import CommentImg from '../../assets/comments.png'
import StarsImg from '../../assets/stars.png'
import HeartImg from '../../assets/Heart.png'

const Podcast: React.FC = () => {
    return (
        <>
            <Header logado={true} nome='Renan' sexo='male'/>
            <Container>
                <Card>
                    <Info>
                        <PodcastCard about='http://republicadomedo.com.br/wp-content/uploads/2017/11/Feed_itunes.jpg'/>
                        <About>
                            <h1>RdM Cast</h1>
                            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Dignissim arcu at faucibus imperdiet blandit. Convallis sollicitudin egestas dolor netus at lectus sed maecenas. Enim ut.</p>
                            <Stats>
                                <img src={StarsImg} />
                                <h1>4.0</h1>
                                <img src={CommentImg} />
                                <h1>10 comentários</h1>
                            </Stats>
                            <Buttons>
                                <a href='/'><FiGlobe color='#fff' size='1.9vw' /></a>
                                <a href='/'><FiMail color='#fff' size='1.9vw' /></a>
                                <a href='/'><FiRss color='#fff' size='1.9vw' /></a>
                                <a href='/'><FiBarChart2 color='#fff' size='1.9vw' /></a>
                            </Buttons>
                        </About>
                        <Subscribe>
                            Inscrito
                        </Subscribe>
                    </Info>
                    <EpisodeCard>
                        <EpisodeImageCard about='http://republicadomedo.com.br/wp-content/uploads/2017/11/Feed_itunes.jpg' />
                        <EpisodeInfo>
                            <h1>O Medo do Medo</h1>
                            <EpisodeStats>
                                <img src={HeartImg} />
                                <h1>50</h1>
                                <img src={CommentImg} />
                                <h1>8 comentários</h1>
                            </EpisodeStats>
                        </EpisodeInfo>
                    </EpisodeCard>
                    <EpisodeCard>
                        <EpisodeImageCard about='http://republicadomedo.com.br/wp-content/uploads/2017/11/Feed_itunes.jpg' />
                        <EpisodeInfo>
                            <h1>O Medo do Medo</h1>
                            <EpisodeStats>
                                <img src={HeartImg} />
                                <h1>50</h1>
                                <img src={CommentImg} />
                                <h1>8 comentários</h1>
                            </EpisodeStats>
                        </EpisodeInfo>
                    </EpisodeCard>

                </Card>
            </Container>
        </>
    )
}

export default Podcast
