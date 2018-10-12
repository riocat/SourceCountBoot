package com.example.sourcecount.base;

import com.example.sourcecount.command.CommandExecute;
import com.example.sourcecount.command.GitCommandExecute;
import com.example.sourcecount.command.LinuxCmdExecute;
import com.example.sourcecount.command.WindowsCmdExecute;
import com.example.sourcecount.entity.ProjectSourceAmount;
import com.example.sourcecount.entity.SourceAmountEntity;
import com.example.sourcecount.util.EmailSend;
import com.example.sourcecount.util.ExcelGeneraor;
import com.example.sourcecount.util.MyFileUtils;
import com.example.sourcecount.util.TxtAnaylsis;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.charts.LayoutMode;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * Created by Administrator on 2018/10/10.
 */
public class LinuxGit {

    public static final String MAIN_PROPERTIES = "base.properties";

    public static void main(String[] args) throws Exception {

        Git git = null;
        PullResult pr = null;
        String gitPath = "/home/rio/git/mygit/cxftest1";
        try {
            System.out.println(gitPath);
            Repository existingRepo = new FileRepositoryBuilder().setGitDir(new File(gitPath + "/.git")).build();
            git = new Git(existingRepo);

            pr = git.pull().setRemoteBranchName("master").call();

            if (!pr.isSuccessful()) {
                throw new RuntimeException(pr.toString());
            }

        } finally {
            if (git != null) {
                git.close();
            }
        }

    }
}
