import React from 'react'
import ScrollMenu from 'react-horizontal-scrolling-menu'
import { FiSearch } from 'react-icons/fi'

import { Card, CardHeader, Container, TitleContainer, Title, HeaderOption, PodcastCard, PodcastContainer, Search, SearchContainer } from './styles'
import heartImg from '../../assets/Heart.png'
import headphonesImg from '../../assets/Headphones.png'

interface Podcast {
    url: string
    key: string
}

const list: Podcast[] = [
    { url: 'http://republicadomedo.com.br/wp-content/uploads/2017/11/Feed_itunes.jpg', key: '1' },
    { url: 'https://hipsters.tech/wp-content/uploads/2016/07/hipsters-logo.png', key: '2' },
    { url: 'https://d3t3ozftmdmh3i.cloudfront.net/production/podcast_uploaded_nologo/528611/528611-1602707577252-eb58a57c5336c.jpg', key: '3' },
    { url: 'https://hipsters.tech/wp-content/uploads/2016/07/hipsters-logo.png', key: '4' },
    { url: 'https://d3t3ozftmdmh3i.cloudfront.net/production/podcast_uploaded_nologo/528611/528611-1602707577252-eb58a57c5336c.jpg', key: '5' },
    { url: 'http://republicadomedo.com.br/wp-content/uploads/2017/11/Feed_itunes.jpg', key: '6' },
]


const MenuItem = ({ url }: Podcast) => {
    return <PodcastCard about={url}/>
}

export const Menu = (): JSX.Element[] =>
    list.map(el => {
        const { key, url } = el

        return <MenuItem url={url} key={key} />
    })

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
                <PodcastContainer>
                    <ScrollMenu
                        alignCenter={false}
                        clickWhenDrag={false}
                        data={Menu()}
                        dragging={true}
                        transition={+0.3}
                        translate={0}
                        wheel={true}
                        wrapperStyle={{
                            overflow: 'hidden'
                        }}
                    />
                </PodcastContainer>
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
                    <SearchContainer>
                        <Search>
                            <button type='submit'>
                                <FiSearch color='#fff' size='2vw'></FiSearch>
                            </button>
                            <input placeholder='Procurar Podcast' />
                        </Search>
                    </SearchContainer>
                    <HeaderOption/>
                </CardHeader>
                <ScrollMenu
                    alignCenter={false}
                    clickWhenDrag={false}
                    data={Menu()}
                    dragging={true}
                    transition={+0.3}
                    translate={0}
                    wheel={true}
                    wrapperStyle={{
                        overflow: 'hidden',
                        padding: '40px 40px 40px 40px'

                    }}
                />
            </Card>
        </Container>
    )
}

export default CardContainer
