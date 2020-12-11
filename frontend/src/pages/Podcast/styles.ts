import styled from 'styled-components'

export const Container = styled.div`
    width: 100vw;
    display: flex;
    justify-content: center;
`

export const Card = styled.div`
    flex: 1;
    max-width: 90vw;
    background: #212325;
    border-radius: 20px;

    padding: 50px 35px;
`

export const Info = styled.div`
    display: flex;
    width: 100%;
    margin: 0 0 4vh 0;
`

export const PodcastCard = styled.div`
    min-width: 20vw;
    min-height: 20vw;

    background-image: url(${ props => props.about }), linear-gradient(270deg, #56525a 0%, rgba(25, 25, 26, 0) 100%);
    background-position: center;
    background-size: cover;
    background-repeat: no-repeat;

    backdrop-filter: blur(42px);
    border-radius: 40px;
    border: 5px solid #161719;
`

export const About = styled.div`
    display: flex;
    flex-direction: column;
    margin-left: 3vw;
    justify-content: space-between;

    color: #fff;

    font-family: 'DM Sans', sans-serif;

    padding: 1vh 0;

    h1 {
        font-size: 5vw;
        font-weight: bold;
    }

    p {
        margin-left: 10px;
        font-size: 1.06vw;
        max-width: 50%;
    }
`

export const EpisodeCard = styled.div`
    flex: 1;
    margin: 0 auto;
    max-width: 96%;
    max-height: 25vh;

    padding: 30px;
    border-radius: 20px;

    background: #292B2D;
`

export const EpisodeImageCard = styled.div`
    width: 6vw;
    height: 6vw;

    background-image: url(${ props => props.about }), linear-gradient(270deg, #56525a 0%, rgba(25, 25, 26, 0) 100%);
    background-position: center;
    background-size: cover;
    background-repeat: no-repeat;

    backdrop-filter: blur(42px);
    border-radius: 14px;
    border: 5px solid #161719;
`

export const Stats = styled.div`
    display: flex;
    max-width: 50%;

    margin-left: 15px;

    img {
        margin-right: 1.5vw;
        width: 2vw;
        height: 2vw;

        background-position: center;
        background-size: cover;
        background-repeat: no-repeat;
    }

    h1 {
        margin-right: 3vw;
        font-family: 'DM Sans', sans-serif;
        color: #fff;
        font-size: 1.7vw;
        font-weight: bold;
    }
`

export const Buttons = styled.div`
    display: flex;
    justify-content: space-between;
    max-width: 16vw;

    margin-left: 15px;
`
