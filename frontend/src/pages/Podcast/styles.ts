import { shade } from 'polished'
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
    margin-bottom: 8vh;
`

export const Info = styled.div`
    display: flex;
    width: 100%;
    margin: 0 0 8vh 0;
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
    min-width: 50%;
    max-width: 53%;

    color: #fff;

    font-family: 'DM Sans', sans-serif;

    padding: 1vh 0;

    h1 {
        font-size: 3.5vw;
        font-weight: bold;
    }

    p {
        margin-left: 10px;
        font-size: 1.06vw;
        max-width: 70%;
    }
`

export const Stats = styled.div`
    display: flex;
    max-width: 70%;

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
    max-width: 26vw;

    margin-left: 15px;
`

export const Subscribe = styled.button`
    width: 22vw;
    max-width: 22vw;
    min-width: 18vw;
    height: 6.8vh;

    background: linear-gradient(9.28deg, #1C1C1C 32.27%, #2E2E2E 68.86%);
    backdrop-filter: blur(42px);

    border-radius: 17px;
    border: 3px solid #474949;

    color: #fff;
    font: 1.7vw 'DM Sans', sans-serif;
        font-weight: bold;

    transition: background-color 0.2s;

    &:hover {
        background: linear-gradient(9.28deg, #000000 32.27%, #171717 68.86%);
    }
`

export const CommentCard = styled.div`
    flex: 1;
    display: flex;
    flex-direction: column;
    margin: 0 auto 40px auto;
    max-width: 96%;
    max-height: 60vh;

    padding: 30px;
    border-radius: 20px;
    justify-content: space-between;

    div {
        overflow-y: scroll;
        overflow-x: visible;
        ::-webkit-scrollbar {
            width: 10px;
            border-radius: 20px;
        }

        /* Track */
        ::-webkit-scrollbar-track {
            background: #292B2D;
            border-radius: 20px;
        }

        /* Handle */
        ::-webkit-scrollbar-thumb {
            background: #161719;
            border-radius: 20px;
        }

        /* Handle on hover */
        ::-webkit-scrollbar-thumb:hover {
            background: #212325;
            border-radius: 20px;
        }
    }

    background: #292B2D;
`
export const Comment = styled.div`
    display: flex;
    justify-content: flex-start;
    align-items: center;
    margin: 0 auto 40px auto;
    width: 96%;

    padding: 30px;
    border-radius: 20px;

    background: #161719;

    p {
        font-family: 'DM Sans', sans-serif;
        color: #fff;
        font-size: 1.4vw;
    }
`

export const SendContainer = styled.form`
    flex: 1;
    width: 96%;
    display: flex;
    justify-content: space-between;
    margin: 20px auto 0 auto;

    button {
        padding: 0 0 0 20px;
        border-radius: 50%;
        width: 1.9vw;
        height: 1.9vw;
        border: none; /* Remove borders */
        background: #292B2D;
        transform: rotate(44deg);
    }

`

export const Message = styled.input`
    display: flex;
    justify-content: flex-start;
    align-items: center;
    width: 100%;
    max-height: 5vh;

    padding: 30px;
    border-radius: 20px;

    border: 0;

    font: 1.06vw 'DM Sans', sans-serif;
    background: #161719;

    color: #fff;

    &::placeholder {
        color: #fff;
    }
`
