package com.kklv.arg_apt;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * @author lvzhendong
 * @data 2023/11/18
 * @description
 */
@AutoService(Processor.class)
public class ArgumentsProcessor extends AbstractProcessor {

    private Elements elementUtils;
    private HashSet<TypeElement> typeMap = new HashSet<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementUtils = processingEnvironment.getElementUtils();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private String getPackageName(TypeElement type) {
        return elementUtils.getPackageOf(type).getQualifiedName().toString();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
//        HashSet<String> supportTypes = new LinkedHashSet<>();
//        supportTypes.add(ArgumentsField.class.getCanonicalName());
        return Collections.singleton(ArgumentsField.class.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println("kklv start process");
        typeMap.clear();
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ArgumentsField.class);

        for (Element element : elements) {
            VariableElement variableElement = (VariableElement) element;
            TypeElement typeElement = (TypeElement) variableElement.getEnclosingElement();

            typeMap.add(typeElement);
        }
        System.out.println("map:" + typeMap.toString());

        for (Element element : typeMap) {
            TypeElement typeElement = (TypeElement) element;

            List<? extends Element> members = elementUtils.getAllMembers(typeElement);
            MethodSpec.Builder bindViewMethodSpecBuilder = MethodSpec.methodBuilder("bindArguments")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(TypeName.VOID)
                    .addParameter(ClassName.get("android.app","Activity"), "activity")
                    .addParameter(ClassName.get(typeElement.asType()), "dataManager");

            for (Element item : members) {
                ArgumentsField diView = item.getAnnotation(ArgumentsField.class);
                if (diView == null) {
                    continue;
                }
                bindViewMethodSpecBuilder.addStatement(String.format("dataManager.%s = (%s) activity.getIntent().getStringExtra(\"%s\")",
                        item.getSimpleName(), ClassName.get(item.asType()).toString(), item.getSimpleName()));

            }
            TypeSpec typeSpec = TypeSpec.classBuilder(typeElement.getSimpleName()+"_ArgumentsBinding")
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(bindViewMethodSpecBuilder.build())
                    .build();

            JavaFile javaFile = JavaFile.builder(getPackageName(typeElement), typeSpec).build();
            try {
                javaFile.writeTo(processingEnv.getFiler());
                System.out.println("kklv 输出文件成功");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("kklv 注解错误");
            }
        }

        return true;
    }
}
