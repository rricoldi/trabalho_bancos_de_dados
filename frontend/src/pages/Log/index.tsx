import React, { useEffect, useState } from 'react'
import { Bar } from 'react-chartjs-2'
import Header from '../../Components/Header'
import api from '../../services/api'

interface IView {
    pod_id: string;
    nAcessos: number;
    pod_nome: string;
}

interface IAge {
    pod_id: string;
    avg_idade: number;
    pod_nome: string;
}

interface IViewT {
    pod_id: string;
    nAcessos: number;
}

interface IAgeT {
    tag: string;
    avg_idade: number;
}

interface ISubs {
    id: string;
    nome: string;
    inscritos: number;
}

interface IStars {
    id: string;
    nome: string;
    classificacao: number;
}

interface IComments {
    id: string;
    nome: string;
    comentarios: number;
}

interface IViewUT {
    acessos: number;
    tag: string;
}

interface IViewU {
    acessos: number;
    pod_nome: string;
}

const Log: React.FC = () => {
    const [views, setViews] = useState<IView[]>([])
    const [ages, setAges] = useState<IAge[]>([])
    const [viewsT, setViewsT] = useState<IViewT[]>([])
    const [agesT, setAgesT] = useState<IAgeT[]>([])
    const [subs, setSubs] = useState<ISubs[]>([])
    const [stars, setStars] = useState<IStars[]>([])
    const [comments, setComments] = useState<IComments[]>([])
    const [viewsU, setViewsU] = useState<IViewU[]>([])
    const [viewsUT, setViewsUT] = useState<IViewUT[]>([])

    const auth = JSON.parse(localStorage.getItem('auth') || '{}')

    useEffect(() => {
        const getViews = async () => {
            const response = await api.get('/logAcesso/podcastsMaisVistos')

            setViews(response.data.acessos.slice(0, 7))
            return response.data.acessos.slice(0, 7)
        }

        getViews()
    }, [])

    useEffect(() => {
        const getAges = async () => {
            const response = await api.get('/logAcesso/mediaIdadesPodcast')
            const aux = []

            for(let i = 0; i < views.length; i++) {
                aux.push(response.data.medias
                    .filter(
                        (media: IAge) => views[i].pod_id === media.pod_id
                    )[0])
            }

            setAges(aux)
        }

        getAges()
    }, [views])

    useEffect(() => {
        const getViewsT = async () => {
            const response = await api.get('/logAcesso/tagsMaisVistas')

            setViewsT(response.data.acessos.slice(0, 7))
            return response.data.acessos.slice(0, 7)
        }

        getViewsT()
    }, [])

    useEffect(() => {
        const getAgesT = async () => {
            const response = await api.get('/logAcesso/mediaIdadesTag')
            const aux = []

            for(let i = 0; i < viewsT.length; i++) {
                aux.push(response.data.medias
                    .filter(
                        (media: IAgeT) => viewsT[i].pod_id === media.tag
                    )[0])
            }

            setAgesT(aux)
        }

        getAgesT()
    }, [viewsT])

    useEffect(() => {
        const getSubs = async () => {
            const response = await api.get('/podcast/PodsMaisInscritos/')
            setSubs(response.data.query.slice(0,10))
        }

        getSubs()
    }, [])

    useEffect(() => {
        const getStars = async () => {
            const response = await api.get('/podcast/PodsMaisClassificados/')
            setStars(response.data.query.slice(0,10))
        }

        getStars()
    }, [])

    useEffect(() => {
        const getComments = async () => {
            const response = await api.get('/podcast/PodsMaisComentados/')
            setComments(response.data.query.slice(0,10))
        }

        getComments()
    }, [])

    useEffect(() => {
        const getViewsU = async () => {
            try {
                const response = await api.get(`/usuario/PodsMaisAcessados/${auth.id}`)
                setViewsU(response.data.query.slice(0,10))
            } catch(error) {
                console.error(error)
            }
        }

        getViewsU()
    }, [])

    useEffect(() => {
        const getViewsUT = async () => {
            try {
                const response = await api.get(`/usuario/TagsMaisAcessadas/${auth.id}`)
                setViewsUT(response.data.query.slice(0,10))
            } catch(error) {
                console.error(error)
            }
        }

        getViewsUT()
    }, [])

    return (
        <>
            <Header/>
            <div>
                {
                    (views.length > 0 && ages.length > 0) && (
                        <Bar
                            data={
                                () => {
                                    return {
                                        labels: views.map((view) => view.pod_nome),
                                        datasets: [{
                                            label: 'Podcasts Mais acessados',
                                            data: views.map(view => view.nAcessos),
                                            backgroundColor: [
                                                'rgb(255, 72, 109)',
                                                'rgb(54, 163, 235)',
                                                'rgb(255, 207, 86)',
                                                'rgb(75, 192, 192)',
                                                'rgb(153, 102, 255)',
                                                'rgb(2, 255, 44)',
                                                'rgb(255, 2, 2)',
                                            ],
                                            borderWidth: 0,
                                        }, {
                                            label: 'Idade média dos usuários que acessam',
                                            data: ages.map(age => age.avg_idade.toFixed(2)),
                                            backgroundColor: [
                                                'rgb(250, 0, 208)',
                                                'rgb(74, 0, 248)',
                                                'rgb(255, 145, 0)',
                                                'rgb(0, 255, 157)',
                                                'rgb(140, 0, 255)',
                                                'rgb(145, 255, 2)',
                                                'rgb(255, 82, 2)',
                                            ],
                                            borderWidth: 0,
                                        }]
                                    }
                                }
                            }
                            width={500}
                            height={450}
                            options={{ maintainAspectRatio: false, scales: { yAxes: [{ ticks: { min: 0, max: (views[0].nAcessos + 10) } }] } }}
                        />
                    )
                }
            </div>
            <div style={{height: 1, borderRadius: 10, backgroundColor: '#6d6d6d', width: '95%', margin: '20px auto'}} />
            <div>
                {
                    (viewsT.length > 0 && agesT.length > 0) && (
                        <Bar
                            data={
                                () => {
                                    return {
                                        labels: viewsT.map((view) => view.pod_id),
                                        datasets: [{
                                            label: 'Tags Mais acessadas',
                                            data: viewsT.map(view => view.nAcessos),
                                            backgroundColor: [
                                                'rgb(54, 163, 235)',
                                                'rgb(2, 255, 44)',
                                                'rgb(75, 192, 192)',
                                                'rgb(255, 72, 109)',
                                                'rgb(255, 2, 2)',
                                                'rgb(255, 207, 86)',
                                                'rgb(153, 102, 255)',
                                            ],
                                            borderWidth: 0,
                                        }, {
                                            label: 'Idade média dos usuários que acessam as Tags',
                                            data: agesT.map(age => age.avg_idade.toFixed(2)),
                                            backgroundColor: [
                                                'rgb(74, 0, 248)',
                                                'rgb(255, 82, 2)',
                                                'rgb(140, 0, 255)',
                                                'rgb(250, 0, 208)',
                                                'rgb(145, 255, 2)',
                                                'rgb(0, 255, 157)',
                                                'rgb(255, 145, 0)',
                                            ],
                                            borderWidth: 0,
                                        }]
                                    }
                                }
                            }
                            width={500}
                            height={450}
                            options={{ maintainAspectRatio: false, scales: { yAxes: [{ ticks: { min: 0, max: (viewsT[0].nAcessos + 10) } }] } }}
                        />
                    )
                }
            </div>
            <div style={{height: 1, borderRadius: 10, backgroundColor: '#6d6d6d', width: '95%', margin: '20px auto'}} />
            <div>
                {
                    (subs.length > 0) && (
                        <Bar
                            data={
                                () => {
                                    return {
                                        labels: subs.map((view) => view.nome),
                                        datasets: [{
                                            label: 'Podcasts com mais inscritos',
                                            data: subs.map(view => view.inscritos),
                                            backgroundColor: [
                                                'rgb(54, 163, 235)',
                                                'rgb(2, 255, 44)',
                                                'rgb(75, 192, 192)',
                                                'rgb(255, 72, 109)',
                                                'rgb(255, 2, 2)',
                                                'rgb(255, 207, 86)',
                                                'rgb(153, 102, 255)',
                                                'rgb(74, 0, 248)',
                                                'rgb(255, 82, 2)',
                                                'rgb(140, 0, 255)',
                                            ],
                                            borderWidth: 0,
                                        }]
                                    }
                                }
                            }
                            width={500}
                            height={450}
                            options={{ maintainAspectRatio: false, scales: { yAxes: [{ ticks: { min: 0, max: (subs[0].inscritos + 10) } }] } }}
                        />
                    )
                }
            </div>
            <div style={{height: 1, borderRadius: 10, backgroundColor: '#6d6d6d', width: '95%', margin: '20px auto'}} />
            <div>
                {
                    (stars.length > 0) && (
                        <Bar
                            data={
                                () => {
                                    return {
                                        labels: stars.map((view) => view.nome),
                                        datasets: [{
                                            label: 'Podcasts mais bem classificados',
                                            data: stars.map(view => view.classificacao.toFixed(2)),
                                            backgroundColor: [
                                                'rgb(54, 163, 235)',
                                                'rgb(2, 255, 44)',
                                                'rgb(75, 192, 192)',
                                                'rgb(255, 72, 109)',
                                                'rgb(255, 2, 2)',
                                                'rgb(255, 207, 86)',
                                                'rgb(153, 102, 255)',
                                                'rgb(74, 0, 248)',
                                                'rgb(255, 82, 2)',
                                                'rgb(140, 0, 255)',
                                            ],
                                            borderWidth: 0,
                                        }]
                                    }
                                }
                            }
                            width={500}
                            height={450}
                            options={{ maintainAspectRatio: false, scales: { yAxes: [{ ticks: { min: 0, max: 5 } }] } }}
                        />
                    )
                }
            </div>
            <div style={{height: 1, borderRadius: 10, backgroundColor: '#6d6d6d', width: '95%', margin: '20px auto'}} />
            <div>
                {
                    (comments.length > 0) && (
                        <Bar
                            data={
                                () => {
                                    return {
                                        labels: comments.map((view) => view.nome),
                                        datasets: [{
                                            label: 'Podcasts mais comentados',
                                            data: comments.map(view => view.comentarios),
                                            backgroundColor: [
                                                'rgb(54, 163, 235)',
                                                'rgb(2, 255, 44)',
                                                'rgb(75, 192, 192)',
                                                'rgb(255, 72, 109)',
                                                'rgb(255, 2, 2)',
                                                'rgb(255, 207, 86)',
                                                'rgb(153, 102, 255)',
                                                'rgb(74, 0, 248)',
                                                'rgb(255, 82, 2)',
                                                'rgb(140, 0, 255)',
                                            ],
                                            borderWidth: 0,
                                        }]
                                    }
                                }
                            }
                            width={500}
                            height={450}
                            options={{ maintainAspectRatio: false, scales: { yAxes: [{ ticks: { min: 0, max: (comments[0].comentarios + 10) } }] } }}
                        />
                    )
                }
            </div>
            <div style={{height: 1, borderRadius: 10, backgroundColor: '#6d6d6d', width: '95%', margin: '20px auto'}} />
            <div>
                {
                    (viewsU.length > 0 && auth.logged) && (
                        <Bar
                            data={
                                () => {
                                    return {
                                        labels: viewsU.map((view) => view.pod_nome),
                                        datasets: [{
                                            label: 'Seus podcast mais acessados',
                                            data: viewsU.map(view => Math.floor(view.acessos)),
                                            backgroundColor: [
                                                'rgb(54, 163, 235)',
                                                'rgb(2, 255, 44)',
                                                'rgb(75, 192, 192)',
                                                'rgb(255, 72, 109)',
                                                'rgb(255, 2, 2)',
                                                'rgb(255, 207, 86)',
                                                'rgb(153, 102, 255)',
                                                'rgb(74, 0, 248)',
                                                'rgb(255, 82, 2)',
                                                'rgb(140, 0, 255)',
                                            ],
                                            borderWidth: 0,
                                        }]
                                    }
                                }
                            }
                            width={500}
                            height={450}
                            options={{ maintainAspectRatio: false, scales: { yAxes: [{ ticks: { min: 0, max: (viewsU[0].acessos + 10) } }] } }}
                        />
                    )
                }
            </div>
            <div style={{height: 1, borderRadius: 10, backgroundColor: '#6d6d6d', width: '95%', margin: '20px auto'}} />
            <div>
                {
                    (viewsUT.length > 0 && auth.logged) && (
                        <Bar
                            data={
                                () => {
                                    return {
                                        labels: viewsUT.map((view) => view.tag),
                                        datasets: [{
                                            label: 'Seus podcast mais acessados',
                                            data: viewsUT.map(view => Math.floor(view.acessos)),
                                            backgroundColor: [
                                                'rgb(54, 163, 235)',
                                                'rgb(2, 255, 44)',
                                                'rgb(75, 192, 192)',
                                                'rgb(255, 72, 109)',
                                                'rgb(255, 2, 2)',
                                                'rgb(255, 207, 86)',
                                                'rgb(153, 102, 255)',
                                                'rgb(74, 0, 248)',
                                                'rgb(255, 82, 2)',
                                                'rgb(140, 0, 255)',
                                            ],
                                            borderWidth: 0,
                                        }]
                                    }
                                }
                            }
                            width={500}
                            height={450}
                            options={{ maintainAspectRatio: false, scales: { yAxes: [{ ticks: { min: 0, max: (viewsUT[0].acessos + 10) } }] } }}
                        />
                    )
                }
            </div>
        </>
    )
}

export default Log
