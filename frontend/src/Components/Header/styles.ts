import styled from 'styled-components'

export const Title = styled.div`
    display: flex;
    align-items: center;

    h1 {
        font: 60px 'Rhodium Libre', serif;
        color: #fff;
        padding-top: 40px;
    }

    img {
        height: 100px;
    }
`

export const Container = styled.div`
    max-height: 15vh;
    min-height: 120px;
    flex: 1;
    display: flex;
    justify-content: space-between;
    align-items: center;
`

export const Button = styled.div`
    flex: 1;
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin: 0 85px 0 0;
    max-width: 400px;

    a {
        font: 18px 'DM Sans', sans-serif;
        font-weight: bold;
        color: #fff;

        text-decoration: none;

        transition: text-decoration 0.5s;

        /* &:hover {
            text-decoration: underline;
        } */
    }
`

export const User = styled.div`
    p {
        font: 12px 'DM Sans', sans-serif;
        font-weight: bold;
        color: #fff;
    }

    a {
        font: 18px 'DM Sans', sans-serif;
        font-weight: bold;
        color: #fff;

        text-decoration: none;
    }
`

