import styled from 'styled-components'
import { shade } from 'polished'

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
    padding: 0 40px;

    background: #212325;
`

export const CardHeader = styled.div`
    flex: 1;
    display: flex;
    justify-content: space-between;
    margin-bottom: 40px;
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

export const PodcastContainer = styled.div`
`

export const PodcastCard = styled.div`
    width: 20vw;
    height: 20vw;

    background-image: url(${ props => props.about }), linear-gradient(270deg, #56525a 0%, rgba(25, 25, 26, 0) 100%);
    background-position: center;
    background-size: cover;
    background-repeat: no-repeat;

    backdrop-filter: blur(42px);
    border-radius: 30px;
    border: 5px solid #161719;
    margin: 0 40px 0 0;
`

export const SearchContainer = styled.div`
    flex: 1;
    display: flex;
    justify-content: flex-end;
    padding: 0 40px 0 0;
`

export const Search = styled.form`
    margin-top: 40px;
    min-width: 22vw;
    height: 3.2vw;

    display: flex;
    align-items: center;
    justify-content: flex-start;

    background: #161719;
    border-radius: 10px;

    input {
        padding: 0 0 0 1vw;
        border-radius: 10px;

        border: 0;
        font: 20px 'DM Sans', sans-serif;
        background: #161719;

        color: #fff;

        &::placeholder {
           color: #fff;
        }
    }

    button {
        padding: 0 0 0 1vw;
        border: 0;
        background: #161719;

        color: #fff;
        transition: background-color 0.2s;
    }
`
