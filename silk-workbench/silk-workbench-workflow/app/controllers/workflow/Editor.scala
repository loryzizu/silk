package controllers.workflow

import controllers.core.{Stream, Widgets}
import org.silkframework.workspace.activity.workflow.{Workflow, OldWorkflowExecutor}
import play.api.mvc.{Action, Controller}
import plugins.Context

class Editor extends Controller {

  def editor(project: String, task: String) = Action { implicit request =>
    val context = Context.get[Workflow](project, task, request.path)
    Ok(views.html.workflow.editor.editor(context))
  }

  def statusStream(projectName: String, taskName: String) = Action { request =>
    val context = Context.get[Workflow](projectName, taskName, request.path)
    val activity = context.task.activity[OldWorkflowExecutor].control
    val stream = Stream.status(activity.status)
    Ok.chunked(Widgets.statusStream(stream, "status"))
  }
}