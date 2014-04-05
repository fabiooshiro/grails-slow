package slow

import org.springframework.dao.DataIntegrityViolationException

class SomeClassController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def dataSource

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        def x = System.currentTimeMillis()
        //SomeClass.findAllByNameLike('%')
        //SomeClass.workaround.list()
        //SomeClass.withCriteria { like('name', '%') }
        SomeClass.list()
        //new groovy.sql.Sql(dataSource).rows("select * from SOME_CLASS where name like '%'")
        params.max = Math.min(max ?: 10, 100)
        def res = [someClassInstanceList: SomeClass.list(params), someClassInstanceTotal: SomeClass.count()]
        log.debug("T = ${System.currentTimeMillis() - x}ms .")
        return res
    }

    def create() {
        [someClassInstance: new SomeClass(params)]
    }

    def save() {
        def someClassInstance = new SomeClass(params)
        if (!someClassInstance.save(flush: true)) {
            render(view: "create", model: [someClassInstance: someClassInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'someClass.label', default: 'SomeClass'), someClassInstance.id])
        redirect(action: "show", id: someClassInstance.id)
    }

    def show(Long id) {
        def someClassInstance = SomeClass.get(id)
        if (!someClassInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'someClass.label', default: 'SomeClass'), id])
            redirect(action: "list")
            return
        }

        [someClassInstance: someClassInstance]
    }

    def edit(Long id) {
        def someClassInstance = SomeClass.get(id)
        if (!someClassInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'someClass.label', default: 'SomeClass'), id])
            redirect(action: "list")
            return
        }

        [someClassInstance: someClassInstance]
    }

    def update(Long id, Long version) {
        def someClassInstance = SomeClass.get(id)
        if (!someClassInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'someClass.label', default: 'SomeClass'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (someClassInstance.version > version) {
                someClassInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'someClass.label', default: 'SomeClass')] as Object[],
                          "Another user has updated this SomeClass while you were editing")
                render(view: "edit", model: [someClassInstance: someClassInstance])
                return
            }
        }

        someClassInstance.properties = params

        if (!someClassInstance.save(flush: true)) {
            render(view: "edit", model: [someClassInstance: someClassInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'someClass.label', default: 'SomeClass'), someClassInstance.id])
        redirect(action: "show", id: someClassInstance.id)
    }

    def delete(Long id) {
        def someClassInstance = SomeClass.get(id)
        if (!someClassInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'someClass.label', default: 'SomeClass'), id])
            redirect(action: "list")
            return
        }

        try {
            someClassInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'someClass.label', default: 'SomeClass'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'someClass.label', default: 'SomeClass'), id])
            redirect(action: "show", id: id)
        }
    }
}
