import React from 'react'
import { FiRss, FiGlobe, FiMail, FiBarChart2 } from 'react-icons/fi'

import { About, Buttons, Card, Container, EpisodeCard, EpisodeImageCard, Info, PodcastCard, Stats } from './styles'
import Header from '../../Components/Header'
import CommentImg from '../../assets/comments.png'
import StarsImg from '../../assets/stars.png'

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
                                <FiGlobe color='#fff' size='1.9vw' />
                                <FiMail color='#fff' size='1.9vw' />
                                <FiRss color='#fff' size='1.9vw' />
                                <FiBarChart2 color='#fff' size='1.9vw' />
                            </Buttons>
                        </About>
                    </Info>
                    <EpisodeCard>
                        <EpisodeImageCard about='http://republicadomedo.com.br/wp-content/uploads/2017/11/Feed_itunes.jpg' />
                    </EpisodeCard>
                </Card>
            </Container>
        </>
    )
}

export default Podcast
