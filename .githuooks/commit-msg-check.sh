#!/usr/bin/env bash

# Color variables for printing
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
RESET='\033[0m'

# Regex patterns
#card_name_regex="(\[project-[0-9]+\]|\[TECH\])" project-card number-type
#person_name_regex="([A-Z][a-z]+.)+"
author_name_regex="\[(${person_name_regex})(\s{0,1}(&|&&)\s{0,1}${person_name_regex})?\]"
work_type_regex="(build|chore|ci|docs|feat|fix|perf|refactor|revert|style|test)"
conventional_commits_regex="^${card_name_regex}${author_name_regex}\s{0,1}${work_type_regex}(\(([a-z \-]+)\))?\!?:\ .+$"
exclude=".^" # Regex used to exclude message commit that match this regex

# Function to read commit message
get_commit_message() {
    cat "$1"
}

# Function to check commit message against regex
check_commit_message() {
    local message=$1
    local regex=$2

    if echo "$message" | grep -Eq "$regex"; then
        return 0
    else
        return 1
    fi
}

# Function to print success message
print_success_message() {
    echo -e "${GREEN}Commit message meets Conventional Commit standards...${RESET}"
}

# Function to print error message
print_error_message() {
    echo -e "${RED}The commit message does not meet the Conventional Commit standard, please check again...${RESET}"
    echo -e "${YELLOW}Example: [PRO-99][Alex Xu] feat: the code changes the world.${RESET}"
    echo -e "${YELLOW}Or for pairs: [PRO-99][Pair DevA & Pair DevB] feat: the code changes the world.${RESET}"
}

# Main execution
commit_message=$(get_commit_message "$1")

# Check if the message matches the exclude regex
if check_commit_message "$commit_message" "$exclude"; then
    print_success_message
    exit 0
fi

# Check the commit message against the conventional commits regex
if check_commit_message "$commit_message" "$conventional_commits_regex"; then
    print_success_message
    exit 0
fi

# If the message does not meet the conventional commit standards
print_error_message
exit 1
