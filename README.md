# awsvault.clj

[![lint - clj](https://github.com/joakimen/awsvault.clj/actions/workflows/lint.yml/badge.svg)](https://github.com/joakimen/awsvault.clj/actions/workflows/lint.yml) [![bb compatible](https://raw.githubusercontent.com/babashka/babashka/master/logo/badge.svg)](https://babashka.org)


Provides helpers for working with [aws-vault](https://github.com/99designs/aws-vault) using [babashka](https://github.com/babashka/babashka).

## Features

- List AWS profiles (with optional pattern)
- Fuzzy-select AWS profiles (with optional pattern)
- Opens a sandboxed Google Chrome instance for a profile
- Open a subshell with AWS credentials for the selected profile

## Requirements

- [99designs/aws-vault](https://github.com/99designs/aws-vault)
- [babashka/babashka](https://github.com/babashka/babashka)
- [junegunn/fzf](https://github.com/junegunn/fzf)

## Install

Using [babashka/bbin](https://github.com/babashka/bbin):

TODO

## Usage

**List** accounts with optional pattern

```sh
# list all profiles
$ awsvault list
company1-dev
company1-prod
company9-dev

# list profiles matching pattern
$ awsvault list --pattern dev
company1-dev
company9-dev
```

**Select** account with pattern

```sh
$ awsvault select --pattern dev

# A profile is selected using fzf..

# │   company1-dev         │
# │ > company9-dev         │
# │   2/2 ──────────────   │
# │ >

company9-dev
```

**Login** a sandboxed Google Chrome instance for a profile

```sh
# With profile fuzzy-selection
$ awsvault login
# A profile is selected using fzf and opened in Google Chrome

# With profile fuzzy-selection and a pattern
$ awsvault login --pattern dev
# A profile matching the specified pattern is selected using fzf and opened in Google Chrome

# With a provided profile
$ awsvault login --profile company1-dev
# company1-dev is opened in Google Chrome
```

**Exec** a profile with aws-vault, providing a subshell with AWS credentials for a profile

```sh
# With profile fuzzy-selection
$ awsvault exec
Starting subshell /opt/homebrew/bin/fish, use `exit` to exit the subshell
# A profile is selected using fzf and opened as a subshell (here, using fish)

# With profile fuzzy-selection and a pattern
$ awsvault exec --pattern dev
Starting subshell /opt/homebrew/bin/fish, use `exit` to exit the subshell
# A profile matching the specified pattern is selected using fzf and opened in a subshell

# With a provided profile
$ awsvault exec --profile company1-dev
Starting subshell /opt/homebrew/bin/fish, use `exit` to exit the subshell
# company1-dev is opened in a subshell
```
