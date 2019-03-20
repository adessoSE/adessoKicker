// Dates defines the x axis on each chart
var dates = [];
var x = 0;
// Other data to track in each chart (defines y axis)
var ranksOverTime = [];
var diffWinLossesOverTime = [];
var winRateOverTime = [];
var winsPerDay = [];
var lossesPerDay = [];

var _winsLastDay = 0;
var _lossesLastDay = 0;

// Get data based on user statistics
[# th:each="statistic : ${statistics}"]

    var rank = [[${statistic.rank}]];
    var diffWinLoose = [[${statistic.wins - statistic.losses}]];
    var wins = [[${statistic.wins}]];
    var losses = [[${statistic.losses}]];
    var winsThisDay = [[${statistic.wins}]] - _winsLastDay;
    var lossesThisDay = [[${statistic.losses}]] - _lossesLastDay;
    var winRate = parseFloat(wins / losses).toFixed(2);

    ranksOverTime.push({x : + x, y : rank});
    diffWinLossesOverTime.push({x : + x, y : diffWinLoose});
    winsPerDay.push({x : + x, y : winsThisDay});
    lossesPerDay.push({x : + x, y : lossesThisDay});
    winRateOverTime.push({x : + x, y : winRate});
    dates.push([[${statistic.date}]]);

    _winsLastDay = [[${statistic.wins}]];
    _lossesLastDay = [[${statistic.losses}]];
    x++;
[/]

var rankOverTimeChart = new Chart($('#canvas-rank-over-time'), {
    type: 'line',
    data: {
        labels: dates,
        datasets: [{
            fill: false,
            borderColor: '#0071B9',
            data: ranksOverTime
        }]
    },
    options: {
        legend: { display: false },
        scales: {
            yAxes: [{
                ticks: {
                    reverse: true,
                }
            }]
        }
    }
});

var winRateOverTimeChart= new Chart($('#canvas-win-rate-over-time'), {
    type: 'line',
    data: {
        labels: dates,
        datasets: [{
            fill: origin,
            borderColor: '#0071B9',
            data: winRateOverTime
        }]
    },
    options: {
        legend: { display: false }
    }
});

var winsAndLossesPerDayChart = new Chart($('#canvas-wins-losses-over-time'), {
    type: 'line',
    data: {
        labels: dates,
        datasets: [
            {
                fill: origin,
                borderColor: '#0071B9',
                data: winsPerDay
            },
            {
                fill: origin,
                borderColor: '#ff2a28',
                backgroundColor: '#ff7978',
                data: lossesPerDay
            }
            ]
    },
    options: {
        legend: { display: false },
    }
});

var winsAndLossesPerDayChart = new Chart($('#canvas-win-difference'), {
    type: 'line',
    data: {
        labels: dates,
        datasets: [{
            fill: origin,
            borderColor: '#0071B9',
            data: diffWinLossesOverTime
        }]
    },
    options: {
        legend: { display: false }
    }
});

