import styled from 'styled-components'
import { shade } from 'polished'

export const Card = styled.div`
    width: 80vw;
    height: 60vh;
    border-radius: 30px;
    margin: 10vh auto;
    flex-direction: column;

    background: #212325;
`


export const Form = styled.form`
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-between;
    padding: 60px 60px 60px 60px;
    height: 100%;

    div {
        display: flex;
        width: 100%;
        justify-content: space-between;
        align-items: center;

        input {
            border-radius: 10px;
            min-width: 25vw;
            height: 3.4vw;
            padding: 0 0 0 1vw;

            border: 0;
            font: 1.06vw 'DM Sans', sans-serif;
            background: #161719;

            color: #fff;

            &::placeholder {
                color: #b5b5b5;
            }
        }

        select {
            border-radius: 10px;
            min-width: 25vw;
            height: 3.4vw;
            padding: 0 0 0 1vw;

            border: 0;
            font: 1.06vw 'DM Sans', sans-serif;
            background: #161719;

            color: #fff;

            &::placeholder {
                color: #b5b5b5;
            }
        }

        h1 {
            color: #fff;
            transition: background-color 0.2s;
            font: 1.2vw 'DM Sans', sans-serif;
            font-weight: bold;
        }
    }

    button {
        width: 20vw;
        height: 8vh;

        background: #1C1C1C;
        backdrop-filter: blur(42px);
        /* Note: backdrop-filter has minimal browser support */

        border-radius: 20px;
        border: 3px solid #474949;

        color: #fff;
        font: 1.8vw 'DM Sans', sans-serif;
            font-weight: bold;

        &:hover {
            background: ${shade(0.2, '#1C1C1C')}
        }
    }
`
