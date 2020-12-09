import styled from 'styled-components'

export const Container = styled.div`
    display: flex;
    flex-direction: column;
    min-height: 85vh - 25px;
    align-items: center;
    justify-content: space-around;
    padding: 25px 0 50px 0;
`

export const Card = styled.div`
    width: 86vw;
    height: 60vh;
    border-radius: 30px;
    margin: 25px 0 50px 0;
    flex-direction: column;

    background: #212325;
`

export const CardHeader = styled.div`
    flex: 1;
    display: flex;
    justify-content: space-between;
`

export const TitleContainer = styled.div`
    display: flex;
    margin: 20px 0 0 20px;
    img {
        height: 85px;
        margin: 0 20px 0 0;
    }
`

export const Title = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    padding: 10px 0 10px 0;
    p {
        font: 14px 'DM Sans', sans-serif;
        color: #fff;
    }

    h1 {
        font: 20px 'DM Sans', sans-serif;
        font-weight: bold;
        color: #fff;

        text-decoration: none;
    }
`

export const HeaderOption = styled.div`
`

export const PodcastCard = styled.div`
    width: 246px;
    height: 242px;
    background: radial-gradient(100% 100% at 0% 0%, rgba(16, 24, 46, 0.4) 0%, rgba(201, 203, 209, 0) 100%);
    backdrop-filter: blur(42px);
    border: 8px solid #2B5AA0;

    border-radius: 30px;
    transform: rotate(90deg);
`
