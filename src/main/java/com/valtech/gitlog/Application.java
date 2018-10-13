package com.valtech.gitlog;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Application {
    public static void main(String... args) throws GitAPIException, IOException {
        Map<String, Integer> commits = new HashMap<>();
        List<String> logMessages = new ArrayList<>();
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repo = builder.setGitDir(new File(args[0])).setMustExist(true).build();
        Git git = new Git(repo);
        Iterable<RevCommit> log = git.log().call();
        for (Iterator<RevCommit> iterator = log.iterator(); iterator.hasNext();) {
            RevCommit rev = iterator.next();
            String mail = rev.getCommitterIdent().getEmailAddress();
            if(commits.containsKey(mail)) {
                commits.put(mail, commits.get(mail).intValue() + 1);
            } else {
                commits.put(mail, 1);
            }

            logMessages.add(rev.getFullMessage());
        }

        commits.forEach((key, value) -> System.out.println(key + "\t" + value));
    }
}
