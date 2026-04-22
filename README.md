# Flashcard CLI - CSA311 HW1
Бие даалт хийж гүйцэтгэв.
## Usage

```
flashcard <cards-file> [options]

Options:
  --help                        Show help information
  --order <order>               Card order (default: random)
                                Options: random, worst-first, recent-mistakes-first
  --repetitions <num>           Required correct answers per card (default: 1)
  --invertCards                 Swap question and answer sides (default: false)
```
## Commands 
```
java -jar target/flashcard-1.0-SNAPSHOT.jar sample_cards.txt -> Үндсэн горимоор ажиллана.
java -jar target/flashcard-1.0-SNAPSHOT.jar sample_cards.txt --order worst-first -> Worst first дараалал ажиллана.
java -jar target/flashcard-1.0-SNAPSHOT.jar sample_cards.txt --invertCards -> Random байдлаар асуулт хариулт солих.
java -jar target/flashcard-1.0-SNAPSHOT.jar --help -> Тусламж
```
## Cards File Format

```
# Comments start with #
Question 1 | Answer 1
Question 2 | Answer 2
```

## Build & Run

```bash
mvn clean package
java -jar target/flashcard-1.0-SNAPSHOT.jar sample_cards.txt --order random --repetitions 2
```

## Run Tests

```bash
mvn test
```

## Generate Site

```bash
mvn site
```

## Achievements

| Achievement | Description |
|-------------|-------------|
| SPEED       | Average answer time under 5 seconds in a round |
| CORRECT     | All cards answered correctly in the last round |
| REPEAT      | A single card answered more than 5 times total |
| CONFIDENT   | A single card answered correctly at least 3 times |
