<!--
PR INSTRUCTIONS 

Title: make a self-explanatory title describing what the pull request does with few words.
Description: detail with what was changed, why it was changed, and how it was changed.
Reviewers: tag the persons that may revise the PR. One member of from team that owns this project is required.
Assignees: please tag yourself into the assignees box.
Labels: choose the appropriate labels, like, if it's a bug, the label will help the reviewers to give priority to this.

Remove the comments before submitting the PR.
-->

## Description

<!-- Please edit the link below to the actual Jira issue                            -->
<!-- (yes, every PR should come from a Jira issue)                                  -->
Link to the Jira issue: [Yard](https://)

<!-- Add your description here -->

## Type of change
<!--
Check that are relevant
In case of "Other", you must explain it
-->

- [ ] Bug fix (non-breaking change which fixes an issue)
- [ ] New feature (non-breaking change which adds functionality)
- [ ] Breaking change (fix or feature that would cause existing functionality to not work as expected)
- [ ] Documentation's update
- [ ] Database's migration
- [ ] Other

## Dependency
<!--
You must explain if this PR have any dependency (and why). If there is not any dependency, you can write "None"
-->

## Author' checklist

- [ ] Have I named this branch as `<Branch type>/<JIRA Issue>`?
- [ ] Have I set the correct tags and milestone for this PR?
- [ ] Have I run the project linters before pushing the changes to github?
- [ ] Have I run the unit tests before pushing the changes to github?
- [ ] Have I run the integration tests before pushing the changes to github?
- [ ] Have I run the e2e tests before pushing the changes to github?
- [ ] Have I reviewed my commit logs to make sure all commits are atomic and well described?
- [ ] Have I kept or increased our test coverage?
- [ ] Have I changed the documentation to reflect the changes in the code (if needed)?
- [ ] Have been any dependent changes merged and published in downstream modules?


## Reviewer' checklist

- [ ] All commits are well written, following the [How to write a good commit message](https://chris.beams.io/posts/git-commit/#seven-rules) principles
- [ ] The tests provided assert the right things and at least the most common use cases
- [ ] There's no coupled components
- [ ] The code do what the Jira issue is describing


## Rollout checklist

- [ ] Do these changes require a database migration?
- [ ] Do these changes require an update in project's changelog?
- [ ] Do these changes require an update in project's configurations?

<!--
Case there's more important things to be done when release, please specify it here
Ex: diagrams, screenshots, external links, instructions...
-->
