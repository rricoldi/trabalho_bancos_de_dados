import React from 'react'

import { Card, CardHeader, Container, TitleContainer, Title, HeaderOption, PodcastCard } from './styles'
import heartImg from '../../assets/Heart.png'
import headphonesImg from '../../assets/Headphones.png'

const CardContainer: React.FC = () => {
    return (
        <Container>
            <Card>
                <CardHeader>
                    <TitleContainer>
                        <img src={heartImg}/>
                        <Title>
                            <h1>Suas Inscrições</h1>
                            <p>Você está inscrito em XX podcasts</p>
                        </Title>
                    </TitleContainer>
                    <HeaderOption/>
                </CardHeader>
                <PodcastCard></PodcastCard>
            </Card>
            <Card>
                <CardHeader>
                    <TitleContainer>
                        <img src={headphonesImg}/>
                        <Title>
                            <h1>Feed de Podcasts</h1>
                            <p>XX podcasts cadastrados</p>
                        </Title>
                    </TitleContainer>
                    <HeaderOption/>

                </CardHeader>
            </Card>
        </Container>
    )
}

export default CardContainer
