import React from 'react'
import { Switch, Route } from 'react-router-dom'

import Feed from '../pages/Feed'

const Routes: React.FC = () => (
    <Switch>
        <Route path="/" exact component={Feed} />
    </Switch>
)

export default Routes
