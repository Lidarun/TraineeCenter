
const _question = document.getElementById('question');
const _options = document.querySelector('.quiz-options');
const _checkBtn = document.getElementById('check-answer');
const _playAgainBtn = document.getElementById('play-again');
const _result = document.getElementById('result');
const _correctScore = document.getElementById('correct-score');
const _totalQuestion = document.getElementById('total-question');

let array = JSON.parse(sessionStorage.getItem("questionsArray"));

let correctAnswer = "", correctScore = askedCount = 0;
const totalQuestion = array.length;

let userAnswer = new Map;
let questionID;

// load question from API
async function loadQuestion() {
    let questionsArray = JSON.parse(sessionStorage.getItem("questionsArray"));
    let question;

    if (questionsArray && questionsArray.length > 0) {
        question = questionsArray.shift();
        questionID = question.id;
        sessionStorage.setItem("questionsArray", JSON.stringify(questionsArray));
    }

    _result.innerHTML = "";
    showQuestion(question);
}

// event listeners
function eventListeners(){
    _checkBtn.addEventListener('click', checkAnswer);
    _playAgainBtn.addEventListener('click', restartQuiz);
}

document.addEventListener('DOMContentLoaded', function() {
    loadQuestion();
    eventListeners();
    _totalQuestion.textContent = totalQuestion;
    _correctScore.textContent = correctScore;
});

// display question and options
function showQuestion(data){
    _checkBtn.disabled = false;
    correctAnswer = data.correctAnswer;
    let incorrectAnswer = data.options;
    let optionsList = incorrectAnswer;
    optionsList.splice(Math.floor(Math.random() * (incorrectAnswer.length + 1)), 0, correctAnswer);

    _question.innerHTML = `${data.question} <br>`;
    _options.innerHTML = `
        ${optionsList.map((option, index) => `
            <li> ${index + 1}. <span>${option}</span> </li>
        `).join('')}
    `;
    selectOption();
}

// options selection
function selectOption(){
    _options.querySelectorAll('li').forEach(function(option){
        option.addEventListener('click', function(){
            if(_options.querySelector('.selected')){
                const activeOption = _options.querySelector('.selected');
                activeOption.classList.remove('selected');
            }
            option.classList.add('selected');
        });
    });
}

// answer checking
function checkAnswer(){
    _checkBtn.disabled = true;
    if(_options.querySelector('.selected')){
        let selectedAnswer = _options.querySelector('.selected span').textContent;

        userAnswer.set(questionID, selectedAnswer);

        if(selectedAnswer === HTMLDecode(correctAnswer)){
            correctScore++;
            _result.innerHTML = `<p><i class = "fas fa-check"></i>Верно!</p>`;
        } else {
            _result.innerHTML = `<p><i class = "fas fa-times"></i>Неверно!</p><small><b>Правильный ответ: </b>${correctAnswer}</small>`;
        }
        checkCount();
    } else {
        _result.innerHTML = `<p><i class = "fas fa-question"></i>Выберите один вариант!</p>`;
        _checkBtn.disabled = false;
    }
}

// to convert html entities into normal text of correct answer if there is any
function HTMLDecode(textString) {
    let doc = new DOMParser().parseFromString(textString, "text/html");
    return doc.documentElement.textContent;
}

function checkCount(){
    askedCount++;
    setCount();
    if(askedCount === totalQuestion){
        sendUserAnswersToServer();

        setTimeout(function(){
            console.log("");
        }, 5000);


        _result.innerHTML += `<p>Ваш результат: ${correctScore}/${totalQuestion} </p>`;
        _playAgainBtn.style.display = "block";
        _checkBtn.style.display = "none";
    } else {
        setTimeout(function(){
            loadQuestion();
        }, 1221);
    }
}

function setCount(){
    _totalQuestion.textContent = totalQuestion;
    _correctScore.textContent = correctScore;
}

function restartQuiz(){
    correctScore = askedCount = 0;
    _playAgainBtn.style.display = "none";
    _checkBtn.style.display = "block";
    _checkBtn.disabled = false;
    setCount();
    loadQuestion().then(r => loadQuestion());
}

function sendUserAnswersToServer() {
    const userAnswerObject = JSON.parse(sessionStorage.getItem("TrialUser"));

    userAnswerObject.result = correctScore;

    // Получение CSRF токена из мета-тегов
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify(userAnswerObject)
    };

    fetch('/trial-test/ort', requestOptions)
        .then(response => response.text()) // Parse the response as text
        .then(data => {
            console.log(data); // The response will be the plain text "Ответы приняты успешно"
        })
        .catch(error => {
            console.error('Error sending user answers:', error);
        });
}